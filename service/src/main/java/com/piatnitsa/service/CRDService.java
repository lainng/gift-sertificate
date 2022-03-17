package com.piatnitsa.service;

import com.piatnitsa.exception.DaoException;

import java.util.List;

public interface CRDService<T> {

    T getById(long id) throws DaoException;
    List<T> getAll();
    void insert(T item);
    void removeById(long id);
}
