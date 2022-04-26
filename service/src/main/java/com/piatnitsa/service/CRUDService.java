package com.piatnitsa.service;

import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;

/**
 * This interface describes CRUD operations for working with objects.
 * @param <T> type of entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface CRUDService<T> extends CRDService<T> {

    /**
     * Updates an {@link T} entity at data source.
     * @param id an ID of specified entity.
     * @param item an updated entity.
     * @throws DaoException if the specified entity not existed at data source.
     * @throws IncorrectParameterException if the ID is not valid or the entity contains not valid data.
     */
    void update(long id, T item) throws DaoException, IncorrectParameterException;
}
