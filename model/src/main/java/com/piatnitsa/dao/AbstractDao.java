package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

public abstract class AbstractDao<T> {
    protected final JdbcTemplate jdbcTemplate;
    protected final ResultSetExtractor<List<T>> resultSetExtractor;

    public AbstractDao(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<T>> resultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.resultSetExtractor = resultSetExtractor;
    }

    public T executeQueryAsSimpleEntity(String query, Object... params) {
        List<T> items = executeQuery(query, params);
        if (items == null || items.size() == 0) {
            return null;
        }
        return items.get(0);
    }

    public List<T> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, resultSetExtractor, params);
    }

    public void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }
}
