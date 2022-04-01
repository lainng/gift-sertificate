package com.piatnitsa.service.impl;

import com.piatnitsa.dao.impl.GiftCertificateDao;
import com.piatnitsa.dao.impl.TagDaoImpl;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GiftCertificateService implements CRUDService<GiftCertificate> {
    private final GiftCertificateDao certificateDao;
    private final TagDaoImpl tagDao;

    @Autowired
    public GiftCertificateService(GiftCertificateDao certificateDao, TagDaoImpl tagDao) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
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
    public void insert(GiftCertificate item) throws DaoException {
        LocalDateTime dateTime = LocalDateTime.now();
        item.setCreateDate(dateTime.toString());
        item.setLastUpdateDate(dateTime.toString());
        saveNewTags(item);
        certificateDao.insert(item);
    }

    @Override
    public void removeById(long id) {
        certificateDao.removeById(id);
    }

    @Override
    public void update(long id, GiftCertificate item) throws DaoException {
        LocalDateTime dateTime = LocalDateTime.now();
        item.setLastUpdateDate(dateTime.toString());
        item.setId(id);
        saveNewTags(item);
        certificateDao.update(item);
    }

    private void saveNewTags(GiftCertificate item) {
        List<Tag> allTags = tagDao.getAll();
        List<Tag> requestTags = item.getTags();
        for (Tag requestTag : requestTags) {
            boolean isExist = false;
            for (Tag tag : allTags) {
                if (requestTag.getName().equalsIgnoreCase(tag.getName())) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                tagDao.insert(requestTag);
            }
        }
    }
}
