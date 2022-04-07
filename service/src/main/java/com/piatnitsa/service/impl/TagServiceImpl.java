package com.piatnitsa.service.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.CRDService;
import com.piatnitsa.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    public void insert(Tag item) throws DaoException {
        tagDao.insert(item);
    }

    @Override
    public List<Tag> doFilter(Map<String, String> params) throws DaoException {
        return tagDao.getWithFilter(params);
    }
}
