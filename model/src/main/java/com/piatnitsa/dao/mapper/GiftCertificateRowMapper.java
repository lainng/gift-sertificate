package com.piatnitsa.dao.mapper;

import com.piatnitsa.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setCreateDate(rs.getString("create_date"));
        certificate.setLastUpdateDate(rs.getString("last_update_date"));
        certificate.setPrice(rs.getBigDecimal("price"));
        return certificate;
    }
}
