package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.extractor.TagExtractor;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private static final String QUERY_SELECT_BY_ID = "select * from tag where id = ?;";
    private static final String QUERY_SELECT_BY_NAME = "select * from tag where tag_name = ?;";
    private static final String QUERY_SELECT_ALL_TAGS = "select * from tag;";
    private static final String QUERY_INSERT_TAG = "insert into tag(tag_name) values (?);";
    private static final String QUERY_DELETE_BY_ID = "delete from tag where id = ?;";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagExtractor resultSetExtractor) {
        super(jdbcTemplate, resultSetExtractor);
    }

    @Override
    public Tag getById(long id) throws DaoException {
        try {
            return executeQueryAsSimpleEntity(QUERY_SELECT_BY_ID, id);
        } catch (DataAccessException e) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public Tag getByName(String name) throws DaoException {
        try {
            return executeQueryAsSimpleEntity(QUERY_SELECT_BY_NAME, name);
        } catch (DataAccessException e) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_NAME);
        }
    }

    @Override
    public List<Tag> getAll() {
        return executeQuery(QUERY_SELECT_ALL_TAGS);
    }

    @Override
    public void insert(Tag item) throws DaoException {
        try {
            executeUpdateQuery(QUERY_INSERT_TAG, item.getName());
        } catch (DataAccessException e) {
            throw new DaoException(DaoExceptionMessageCodes.SAVING_ERROR);
        }
    }

    @Override
    public void removeById(long id) throws DaoException {
        try {
            executeUpdateQuery(QUERY_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            throw new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID);
        }
    }
}
