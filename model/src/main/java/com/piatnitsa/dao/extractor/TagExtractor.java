package com.piatnitsa.dao.extractor;

import com.piatnitsa.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of the {@link ResultSetExtractor} interface
 * and is designed to work with a <code>tag</code> table to display ResultSet rows for each row.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@Component
public class TagExtractor implements ResultSetExtractor<List<Tag>> {

    @Override
    public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Tag> tags = new ArrayList<>();
        while (rs.next()) {
            Tag tag = new Tag();
            tag.setId(rs.getLong("id"));
            tag.setName(rs.getString("tag_name"));
            tags.add(tag);
        }
        return tags;
    }
}
