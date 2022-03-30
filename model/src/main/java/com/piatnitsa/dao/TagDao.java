package com.piatnitsa.dao;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;

public interface TagDao extends CRDDao<Tag>{
    Tag getByName(String name) throws DaoException;
}
