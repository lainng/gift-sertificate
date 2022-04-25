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

    /**
     * Retrieves a {@link T} object by its ID.
     * @param id The ID of the object.
     * @return A {@link T} object.
     * @throws DaoException if {@link T} object not found.
     */
    T getById(long id) throws DaoException;

    /**
     * Retrieves a {@link List} of {@link T} objects.
     * @return A {@link List} of {@link T} objects.
     * @throws DaoException if {@link List} of {@link T} objects is empty.
     */
    List<T> getAll() throws DaoException;

    /**
     * Method for saving an {@link T} entity.
     * @param item {@link T} entity to save.
     * @throws DaoException if errors occurred while saving.
     */
    void insert(T item) throws DaoException;

    /**
     * Removes an {@link T} entity from data source by its ID.
     * @param id The ID of {@link T} entity.
     * @throws DaoException if entity with specified ID not existed.
     */
    void removeById(long id) throws DaoException;
}
