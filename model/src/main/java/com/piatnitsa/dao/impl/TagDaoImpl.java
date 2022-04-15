package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.QueryBuilder;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.extractor.TagExtractor;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private static final String QUERY_SELECT_BY_ID = "select * from tag where id = ?;";
    private static final String QUERY_SELECT_BY_NAME = "select * from tag where tag_name ilike ?;";
    private static final String QUERY_SELECT_ALL_TAGS = "select * from tag ";
    private static final String QUERY_INSERT_TAG = "insert into tag(tag_name) values (?);";
    private static final String QUERY_DELETE_BY_ID = "delete from tag where id = ?;";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagExtractor resultSetExtractor) {
        super(jdbcTemplate, resultSetExtractor);
    }

    @Override
    public Tag getById(long id) throws DaoException {
        Tag item = executeQueryAsSingleEntity(QUERY_SELECT_BY_ID, id);
        if (item == null) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID);
        }
        return item;
    }

    @Override
    public Tag getByName(String name) throws DaoException {
        Tag item = executeQueryAsSingleEntity(QUERY_SELECT_BY_NAME, name);
        if (item == null) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_NAME);
        }
        return item;
    }

    @Override
    public List<Tag> getWithFilter(Map<String, String> params) throws DaoException {
        QueryBuilder queryBuilder = new QueryBuilder();
        String getQuery = queryBuilder.buildQueryWithFilters(QUERY_SELECT_ALL_TAGS, params);
        List<Tag> filteredTags = executeQuery(getQuery);
        if (filteredTags.isEmpty()) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITIES_WITH_PARAMETERS);
        }
        return filteredTags;
    }

    @Override
    public List<Tag> getAll() {
        return executeQuery(QUERY_SELECT_ALL_TAGS);
    }

    @Override
    public void insert(Tag item) {
        executeUpdateQuery(QUERY_INSERT_TAG, item.getName());
    }

    @Override
    public void removeById(long id) throws DaoException {
        int affectedRows = executeUpdateQuery(QUERY_DELETE_BY_ID, id);
        if (affectedRows == 0) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID);
        }
    }
}
