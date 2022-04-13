package com.piatnitsa.service;

import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;

import java.util.List;

public interface CRDService<T> {

    T getById(long id) throws DaoException, IncorrectParameterException;
    List<T> getAll();
    void insert(T item) throws DaoException, IncorrectParameterException;
    void removeById(long id) throws DaoException, IncorrectParameterException;
}
