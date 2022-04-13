package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.QueryBuilder;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.extractor.GiftCertificateExtractor;
import com.piatnitsa.dao.extractor.GiftCertificateFieldExtractor;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {
    private static final String QUERY_SELECT_CERTIFICATE_ID = "select gc.id from gift_certificate gc left join gift_certificate_with_tags gcwt on gc.id = gcwt.gift_certificate_id left join tag t on t.id = gcwt.tag_id ";
    private static final String QUERY_SELECT_BY_ID = "select * from gift_certificate gc left join gift_certificate_with_tags gcwt on gc.id = gcwt.gift_certificate_id left join tag t on t.id = gcwt.tag_id where gc.id = ?;";
    private static final String QUERY_SELECT_ALL_CERTIFICATES = "select * from gift_certificate gc left join gift_certificate_with_tags gcwt on gc.id = gcwt.gift_certificate_id left join tag t on t.id = gcwt.tag_id ";
    private static final String QUERY_DELETE_BY_ID = "delete from gift_certificate where id = ?;";
    private static final String QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE = "insert into gift_certificate_with_tags values (?, ?);";
    private static final String QUERY_INSERT_NEW_CERTIFICATE = "insert into gift_certificate(name, description, duration, create_date, last_update_date, price) values (?, ?, ?, ?, ?, ?);";
    private static final String QUERY_DELETE_ASSOCIATED_TAGS = "delete from gift_certificate_with_tags where gift_certificate_id = ?";
    private static final String QUERY_UPDATE_CERTIFICATE = "update gift_certificate set ";
    private final TagDao tagDao;
    private final QueryBuilder queryBuilder;
    private final GiftCertificateFieldExtractor fieldExtractor;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  TagDao tagDao,
                                  GiftCertificateExtractor giftCertificateExtractor,
                                  QueryBuilder queryBuilder,
                                  GiftCertificateFieldExtractor fieldExtractor) {
        super(jdbcTemplate, giftCertificateExtractor);
        this.tagDao = tagDao;
        this.queryBuilder = queryBuilder;
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public GiftCertificate getById(long id) throws DaoException {
        GiftCertificate item = executeQueryAsSingleEntity(QUERY_SELECT_BY_ID, id);
        if (item == null) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID);
        }
        return item;
    }

    @Override
    public List<GiftCertificate> getAll() {
        return executeQuery(QUERY_SELECT_ALL_CERTIFICATES);
    }

    @Override
    public void insert(GiftCertificate item) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                (connection) -> {
                    PreparedStatement ps = connection.prepareStatement(
                            QUERY_INSERT_NEW_CERTIFICATE,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, item.getName());
                    ps.setString(2, item.getDescription());
                    ps.setInt(3, item.getDuration());
                    ps.setString(4, item.getCreateDate());
                    ps.setString(5, item.getLastUpdateDate());
                    ps.setBigDecimal(6, item.getPrice());
                    return ps;
                },
                keyHolder
        );
        Integer newId;
        if (keyHolder.getKeys().size() > 1) {
            newId = (Integer) keyHolder.getKeys().get("id");
        } else {
            newId = keyHolder.getKey().intValue();
        }
        item.setId(newId);
        addNewTagsToCertificate(item);
    }

    @Override
    public void removeById(long id) throws DaoException {
        int affectedRows = executeUpdateQuery(QUERY_DELETE_BY_ID, id);
        if (affectedRows == 0) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public void update(GiftCertificate item) throws DaoException {
        Map<String, String> params = fieldExtractor.extractData(item);
        executeUpdateQuery(queryBuilder.buildUpdateQuery(QUERY_UPDATE_CERTIFICATE, params));
        updateCertificateTags(item);
    }

    @Override
    public List<GiftCertificate> getWithFilter(Map<String, String> params) throws DaoException {
        String query = queryBuilder.buildQueryWithFilters(
                QUERY_SELECT_CERTIFICATE_ID,
                params);
        List<Integer> ids = jdbcTemplate.queryForList(query, Integer.class);
        ids = ids.stream().distinct().collect(Collectors.toList());
        List<GiftCertificate> filteredCertificates = new ArrayList<>(ids.size());
        for (Integer id : ids) {
            GiftCertificate item = executeQueryAsSingleEntity(
                    QUERY_SELECT_BY_ID,
                    id
            );
            filteredCertificates.add(item);
        }
        return filteredCertificates;
    }

    private void updateCertificateTags(GiftCertificate item) throws DaoException {
        List<Tag> requestTags = item.getTags();
        if (requestTags == null || requestTags.size() == 0) {
            return;
        }

        List<Tag> newTags = createTagsWithId(item.getTags());
        executeUpdateQuery(
                QUERY_DELETE_ASSOCIATED_TAGS,
                item.getId()
        );
        newTags.forEach((newTag) ->
                executeUpdateQuery(
                    QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE,
                    item.getId(),
                    newTag.getId()
                )
        );
    }

    private void addNewTagsToCertificate(GiftCertificate item) throws DaoException {
        List<Tag> newTags = createTagsWithId(item.getTags());
        for (Tag newTag : newTags) {
            executeUpdateQuery(
                    QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE,
                    item.getId(),
                    newTag.getId()
            );
        }
    }

    private List<Tag> createTagsWithId(List<Tag> requestTags) throws DaoException {
        List<Tag> newTagsWithId = new ArrayList<>(requestTags.size());
        for (Tag requestTag : requestTags) {
            Tag tagWithId = tagDao.getByName(requestTag.getName());
            newTagsWithId.add(tagWithId);
        }
        return newTagsWithId;
    }
}
