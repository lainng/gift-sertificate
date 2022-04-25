package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;

import java.util.List;

/**
 * This interface describes CRD operations for working with database tables.
 * @param <T> type of entity.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface CRDDao<T> {
    T getById(long id) throws DaoException;
    List<T> getAll() throws DaoException;
    void insert(T item) throws DaoException;
    void removeById(long id) throws DaoException;
}
