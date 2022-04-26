package com.piatnitsa.service;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;

import java.util.List;
import java.util.Map;

/**
 * This interface describes abstract behavior for working with {@link Tag} objects.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface TagService extends CRDService<Tag> {

    /**
     * Method for getting a list of {@link Tag} by specific parameters.
     * @param params request parameters from URL.
     * @return {@link List} of {@link Tag}.
     * @throws DaoException if entities by specified parameters not found.
     * @throws IncorrectParameterException if specified parameters contains incorrect parameters.
     */
    List<Tag> doFilter(Map<String, String> params) throws DaoException, IncorrectParameterException;
}
