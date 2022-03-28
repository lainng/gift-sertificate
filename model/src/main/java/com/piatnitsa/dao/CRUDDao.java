package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;

public interface CRUDDao<T> extends CRDDao<T> {
    void update(T item) throws DaoException;
}
