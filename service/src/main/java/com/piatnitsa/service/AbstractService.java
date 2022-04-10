package com.piatnitsa.service;

import com.piatnitsa.dao.CRDDao;
import com.piatnitsa.exception.DaoException;

import java.util.List;

public abstract class AbstractService<T> implements CRDService<T> {
    private final CRDDao<T> dao;

    protected AbstractService(CRDDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T getById(long id) throws DaoException {
        return dao.getById(id);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public void removeById(long id) throws DaoException {
        dao.removeById(id);
    }
}
