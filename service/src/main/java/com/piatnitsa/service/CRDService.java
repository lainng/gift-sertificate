package com.piatnitsa.service;

import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;

import java.util.List;

/**
 * This interface describes CRD operations for working with objects.
 * @param <T> type of entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface CRDService<T> {

    /**
     * Retrieves a {@link T} object by its ID.
     * @param id An ID of the object.
     * @return A {@link T} object.
     * @throws DaoException if {@link T} object not found.
     * @throws IncorrectParameterException if the ID is not valid.
     */
    T getById(long id) throws DaoException, IncorrectParameterException;

    /**
     * Retrieves a {@link List} of {@link T} objects.
     * @return A {@link List} of {@link T} objects.
     * @throws DaoException if {@link T} entities are not found.
     */
    List<T> getAll() throws DaoException;

    /**
     * Method for saving an {@link T} entity
     * @param item an {@link T} entity to save.
     * @throws DaoException if errors occurred while saving.
     * @throws IncorrectParameterException if the {@link T} entity contains not valid data.
     */
    void insert(T item) throws DaoException, IncorrectParameterException;

    /**
     * Removes an {@link T} entity from data source by its ID.
     * @param id an ID of {@link T} entity.
     * @throws DaoException if the {@link T} entity with specified ID not existed.
     * @throws IncorrectParameterException if the ID is not valid.
     */
    void removeById(long id) throws DaoException, IncorrectParameterException;
}
