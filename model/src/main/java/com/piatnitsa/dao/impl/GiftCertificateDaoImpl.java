package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.*;
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
import java.util.Set;

@Component
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {
    private static final String QUERY_SELECT_BY_ID = "select * from gift_certificate gc left join gift_certificate_with_tags gcwt on gc.id = gcwt.gift_certificate_id left join tag t on t.id = gcwt.tag_id where gc.id = ?;";
    private static final String QUERY_SELECT_ALL_CERTIFICATES = "select * from gift_certificate gc left join gift_certificate_with_tags gcwt on gc.id = gcwt.gift_certificate_id left join tag t on t.id = gcwt.tag_id ";
    private static final String QUERY_DELETE_BY_ID = "delete from gift_certificate where id = ?;";
    private static final String QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE = "insert into gift_certificate_with_tags values (?, ?);";
    private static final String QUERY_DELETE_OLD_TAGS_FROM_CERTIFICATE = "delete from gift_certificate_with_tags where gift_certificate_id = ? and tag_id = ?;";
    private static final String QUERY_INSERT_NEW_CERTIFICATE = "insert into gift_certificate(name, description, duration, create_date, last_update_date, price) values (?, ?, ?, ?, ?, ?);";
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  TagDao tagDao,
                                  GiftCertificateExtractor giftCertificateExtractor) {
        super(jdbcTemplate, giftCertificateExtractor);
        this.tagDao = tagDao;
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
        executeUpdateQuery(buildUpdateQuery(item));
        updateCertificateTags(item);
    }

    @Override
    public List<GiftCertificate> getWithFilter(Map<String, String> params) throws DaoException {
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.buildParametrizedQuery(QUERY_SELECT_ALL_CERTIFICATES, params);
        return jdbcTemplate.query(query, resultSetExtractor);
    }

    private String buildUpdateQuery(GiftCertificate item) {
        StringBuilder updateQuery = new StringBuilder("update gift_certificate set ");
        GiftCertificateFieldExtractor extractor = new GiftCertificateFieldExtractor();

        Map<String, String> fields = extractor.extractData(item);
        Set<Map.Entry<String, String>> entries = fields.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            updateQuery.append(entry.getKey())
                    .append("=")
                    .append('\'').append(entry.getValue()).append('\'')
                    .append(", ");
        }
        updateQuery.deleteCharAt(updateQuery.length() - 2);
        updateQuery.append(" where id=").append(item.getId());

        return updateQuery.toString();
    }

    private void updateCertificateTags(GiftCertificate item) throws DaoException {
        List<Tag> newTags = createTagsWithId(item.getTags());
        GiftCertificate certificateFromDB = getById(item.getId());

        List<Tag> oldTags = certificateFromDB.getTags();
        List<Tag> newTagsTempList = new ArrayList<>(newTags);
        newTags.removeAll(oldTags);
        oldTags.removeAll(newTagsTempList);

        newTags.forEach((newTag) ->
                executeUpdateQuery(
                    QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE,
                    item.getId(),
                    newTag.getId()
                )
        );

        oldTags.forEach((oldTag) ->
                executeUpdateQuery(
                        QUERY_DELETE_OLD_TAGS_FROM_CERTIFICATE,
                        item.getId(),
                        oldTag.getId()
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
