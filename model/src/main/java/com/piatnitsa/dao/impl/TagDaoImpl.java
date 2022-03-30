package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.CRDDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.mapper.TagRowMapper;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
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
                .orElseThrow(() -> new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID));
    }

    @Override
    public Tag getByName(String name) throws DaoException {
        return jdbcTemplate.query(
                "select * from tag where tag_name = ?;",
                new TagRowMapper(),
                name
        ).stream()
                .findFirst()
                .orElseThrow(() -> new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID));
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
                "insert into tag(tag_name) values (?);",
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
