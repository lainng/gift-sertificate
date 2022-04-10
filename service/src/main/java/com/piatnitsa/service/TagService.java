package com.piatnitsa.service;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;

import java.util.List;
import java.util.Map;

public interface TagService extends CRDService<Tag> {
    List<Tag> doFilter(Map<String, String> params) throws DaoException, IncorrectParameterException;
}
