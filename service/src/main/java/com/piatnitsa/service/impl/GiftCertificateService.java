package com.piatnitsa.service.impl;

import com.piatnitsa.dao.impl.GiftCertificateDao;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GiftCertificateService implements CRUDService<GiftCertificate> {
    private final GiftCertificateDao certificateDao;

    @Autowired
    public GiftCertificateService(GiftCertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public GiftCertificate getById(long id) throws DaoException {
        return certificateDao.getById(id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return certificateDao.getAll();
    }

    @Override
    public void insert(GiftCertificate item) {
        LocalDateTime dateTime = LocalDateTime.now();
        item.setCreateDate(dateTime.toString());
        item.setLastUpdateDate(dateTime.toString());
        certificateDao.insert(item);
    }

    @Override
    public void removeById(long id) {
        certificateDao.removeById(id);
    }

    @Override
    public void update(long id, GiftCertificate item) {
        LocalDateTime dateTime = LocalDateTime.now();
        item.setLastUpdateDate(dateTime.toString());
        item.setId(id);
        certificateDao.update(item);
    }
}
