package com.piatnitsa.dao;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {
    List<GiftCertificate> getWithFilter(Map<String, String> params) throws DaoException;
}
