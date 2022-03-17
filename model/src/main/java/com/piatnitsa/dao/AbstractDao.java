package com.piatnitsa.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class AbstractDao<T> {
    protected final JdbcTemplate jdbcTemplate;

    public AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
