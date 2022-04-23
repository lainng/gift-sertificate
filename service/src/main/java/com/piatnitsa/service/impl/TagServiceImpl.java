package com.piatnitsa.service.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.FilterParameter;
import com.piatnitsa.service.TagService;
import com.piatnitsa.validator.FilterParameterValidator;
import com.piatnitsa.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    public void insert(Tag item) throws DaoException, IncorrectParameterException {
        TagValidator.validate(item);
        tagDao.insert(item);
    }

    @Override
    public List<Tag> doFilter(Map<String, String> params) throws DaoException, IncorrectParameterException {
        return tagDao.getWithFilter(orderParameters(params));
    }

    private Map<String, String> orderParameters(Map<String, String> requestParams) throws IncorrectParameterException {
        Map<String, String> orderedParams = new LinkedHashMap<>(requestParams.size());
        Map<String, String> requestParamsCopy = new HashMap<>(requestParams);

        if (requestParamsCopy.containsKey(FilterParameter.TAG_NAME)) {
            orderedParams.put(FilterParameter.TAG_NAME, requestParamsCopy.get(FilterParameter.TAG_NAME));
            requestParamsCopy.remove(FilterParameter.TAG_NAME);
        }
        if (requestParamsCopy.containsKey(FilterParameter.SORT_BY_TAG_NAME)) {
            String sortType = requestParamsCopy.get(FilterParameter.SORT_BY_TAG_NAME);
            FilterParameterValidator.validateSortType(sortType);

            orderedParams.put(FilterParameter.SORT_BY_TAG_NAME, sortType);
            requestParamsCopy.remove(FilterParameter.SORT_BY_TAG_NAME);
        }

        if (!requestParamsCopy.isEmpty()) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_TAG_FILTER_PARAMETER);
        }
        return orderedParams;
    }
}
