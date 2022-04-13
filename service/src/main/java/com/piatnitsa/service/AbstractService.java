package com.piatnitsa.service;

import com.piatnitsa.dao.CRDDao;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.validator.IdentifiableValidator;

import java.util.List;

public abstract class AbstractService<T> implements CRDService<T> {
    private final CRDDao<T> dao;

    protected AbstractService(CRDDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T getById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        return dao.getById(id);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public void removeById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        dao.removeById(id);
    }
}
