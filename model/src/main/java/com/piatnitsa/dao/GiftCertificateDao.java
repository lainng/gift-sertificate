package com.piatnitsa.dao;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * This interface describes abstract behavior for working with <code>gift_certificate</code> table in database.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    /**
     * Method for getting a list of {@link GiftCertificate} by specific parameters.
     * @param params request parameters from URL.
     * @return {@link List} of {@link GiftCertificate}.
     * @throws DaoException if entities by specified parameters not found.
     */
    List<GiftCertificate> getWithFilter(Map<String, String> params) throws DaoException;
}
