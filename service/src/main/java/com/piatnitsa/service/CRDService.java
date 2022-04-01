package com.piatnitsa.service;

import com.piatnitsa.exception.DaoException;

import java.util.List;

public interface CRDService<T> {

    T getById(long id) throws DaoException;
    List<T> getAll() throws DaoException;
    void insert(T item) throws DaoException;
    void removeById(long id) throws DaoException;
}
