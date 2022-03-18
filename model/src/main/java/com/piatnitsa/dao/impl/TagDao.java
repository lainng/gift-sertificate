package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.CRDDao;
import com.piatnitsa.dao.TagRowMapper;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDao extends AbstractDao<Tag> implements CRDDao<Tag> {

    @Autowired
    public TagDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Tag getById(long id) throws DaoException {
        return jdbcTemplate.query(
                        "select * from tag where id = ?;",
                        new TagRowMapper(),
                        id
                ).stream()
                .findFirst()
                .orElseThrow(() -> new DaoException("404001"));
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(
                "select * from tag;",
                new TagRowMapper()
        );
    }

    @Override
    public void insert(Tag item) {
        jdbcTemplate.update(
                "insert into tag(name) values (?);",
                item.getName()
        );
    }

    @Override
    public void removeById(long id) {
        jdbcTemplate.update(
                "delete from tag where id = ?;",
                id
        );
    }
}
