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

    Tag getByName(String name) throws DaoException;
    List<Tag> getWithFilter(Map<String, String> params) throws DaoException;
}
