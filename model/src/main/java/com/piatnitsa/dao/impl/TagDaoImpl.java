package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.extractor.TagExtractor;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDaoImpl extends AbstractDao<Tag> implements com.piatnitsa.dao.TagDao {

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Tag getById(long id) throws DaoException {
        return jdbcTemplate.query(
                        "select * from tag where id = ?;",
                        new TagExtractor(),
                        id
                ).stream()
                .findFirst()
                .orElseThrow(() -> new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID));
    }

    @Override
    public Tag getByName(String name) throws DaoException {
        return jdbcTemplate.query(
                "select * from tag where tag_name = ?;",
                new TagExtractor(),
                name
        ).stream()
                .findFirst()
                .orElseThrow(() -> new DaoException(DaoExceptionMessageCodes.NO_ENTITY_WITH_ID));
    }


    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(
                "select * from tag;",
                new TagExtractor()
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
