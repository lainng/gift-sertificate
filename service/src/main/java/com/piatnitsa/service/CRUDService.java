package com.piatnitsa.service;

import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;

public interface CRUDService<T> extends CRDService<T> {
    void update(long id, T item) throws DaoException, IncorrectParameterException;
}
