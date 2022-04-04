package com.piatnitsa.service.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements CRDService<Tag> {
    private final TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag getById(long id) throws DaoException {
        return tagDao.getById(id);
    }

    @Override
    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    @Override
    public void insert(Tag item) throws DaoException {
        tagDao.insert(item);
    }

    @Override
    public void removeById(long id) {
        tagDao.removeById(id);
    }
}
