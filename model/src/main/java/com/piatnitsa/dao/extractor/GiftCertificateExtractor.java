package com.piatnitsa.dao.extractor;

import com.piatnitsa.entity.GiftCertificate;
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
 * and is designed to work with a <code>gift_certificate</code> table to display ResultSet rows for each row.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@Component
public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> certificates = new ArrayList<>();
        boolean hasRows = rs.next();

        while (hasRows && !rs.isAfterLast()) {
            GiftCertificate certificate = new GiftCertificate();
            certificate.setId(rs.getLong("id"));
            certificate.setName(rs.getString("name"));
            certificate.setDescription(rs.getString("description"));
            certificate.setDuration(rs.getInt("duration"));
            certificate.setCreateDate(rs.getString("create_date"));
            certificate.setLastUpdateDate(rs.getString("last_update_date"));
            certificate.setPrice(rs.getBigDecimal("price"));

            List<Tag> tags = extractTagList(certificate.getId(), rs);
            certificate.setTags(tags);
            certificates.add(certificate);
        }
        return certificates;
    }

    private List<Tag> extractTagList(long certificateId, ResultSet rs) throws SQLException {
        List<Tag> tags = new ArrayList<>();
        while (!rs.isAfterLast()
                && rs.getLong("id") == certificateId
                && rs.getLong("tag_id") != 0) {
            Tag tag = new Tag();
            tag.setId(rs.getLong("tag_id"));
            tag.setName(rs.getString("tag_name"));
            tags.add(tag);
            rs.next();
        }
        if (tags.size() == 0) {
            rs.next();
        }
        return tags;
    }
}
