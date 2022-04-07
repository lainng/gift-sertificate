package com.piatnitsa.dao;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface TagDao extends CRDDao<Tag>{

    Tag getByName(String name) throws DaoException;
    List<Tag> getWithFilter(Map<String, String> params) throws DaoException;
}
