package com.piatnitsa.dao;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * This interface describes abstract behavior for working with <code>tag</code> table in database.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface TagDao extends CRDDao<Tag>{

    /**
     * Retrieves an {@link Tag} entity by its name.
     * @param name entity name.
     * @return an {@link Tag} entity.
     * @throws DaoException if entity with specified name not existed.
     */
    Tag getByName(String name) throws DaoException;

    /**
     * Method for getting a list of {@link Tag} by specific parameters.
     * @param params request parameters from URL.
     * @return {@link List} of {@link Tag}.
     * @throws DaoException if entities by specified parameters not found.
     */
    List<Tag> getWithFilter(Map<String, String> params) throws DaoException;
}
