package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;

import java.util.List;

public interface CRDDao<T> {
    T getById(long id) throws DaoException;
    List<T> getAll();
    void insert(T item) throws DaoException;
    void removeById(long id);
}
