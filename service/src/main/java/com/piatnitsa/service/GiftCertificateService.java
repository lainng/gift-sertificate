package com.piatnitsa.service;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends CRUDService<GiftCertificate> {
    List<GiftCertificate> doFilter(Map<String, String> params) throws DaoException;
}
