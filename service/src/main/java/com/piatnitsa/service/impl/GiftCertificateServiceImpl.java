package com.piatnitsa.service.impl;

import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.impl.GiftCertificateDaoImpl;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.IncorrectParameterMessageCodes;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.FilterParameter;
import com.piatnitsa.service.GiftCertificateService;
import com.piatnitsa.service.TimestampHandler;
import com.piatnitsa.validator.FilterParameterValidator;
import com.piatnitsa.validator.GiftCertificateValidator;
import com.piatnitsa.validator.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate> implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, TagDao tagDao) {
        super(certificateDao);
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
    }

    @Override
    public void insert(GiftCertificate item) throws DaoException, IncorrectParameterException {
        String currentTimestamp = TimestampHandler.getCurrentTimestamp();
        item.setCreateDate(currentTimestamp);
        item.setLastUpdateDate(currentTimestamp);
        GiftCertificateValidator.validate(item);
        saveNewTags(item);
        certificateDao.insert(item);
    }

    @Override
    public void update(long id, GiftCertificate item) throws DaoException, IncorrectParameterException {
        String currentTimestamp = TimestampHandler.getCurrentTimestamp();
        item.setLastUpdateDate(currentTimestamp);

        IdentifiableValidator.validateId(id);
        GiftCertificateValidator.validateForUpdate(item);

        item.setId(id);
        saveNewTags(item);

        certificateDao.update(item);
    }

    @Override
    public List<GiftCertificate> doFilter(Map<String, String> params) throws DaoException, IncorrectParameterException {
        return certificateDao.getWithFilter(orderParameters(params));
    }

    private void saveNewTags(GiftCertificate item) throws DaoException {
        List<Tag> allTags = tagDao.getAll();
        List<Tag> requestTags = item.getTags();
        if (requestTags == null || requestTags.size() == 0) {
            return;
        }

        for (Tag requestTag : requestTags) {
            boolean isExist = false;
            for (Tag tag : allTags) {
                if (requestTag.getName().equalsIgnoreCase(tag.getName())) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                tagDao.insert(requestTag);
            }
        }
    }

    private Map<String, String> orderParameters(Map<String, String> requestParams) throws IncorrectParameterException {
        Map<String, String> filterParams = new LinkedHashMap<>(requestParams.size());
        Map<String, String> sortingParams = new LinkedHashMap<>(requestParams.size());
        Map<String, String> requestParamsCopy = new HashMap<>(requestParams);

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case FilterParameter.NAME:
                case FilterParameter.DESCRIPTION:
                case FilterParameter.TAG_NAME: {
                    filterParams.put(key, value);
                    requestParamsCopy.remove(key);
                    break;
                }
                case FilterParameter.DATE_SORT:
                case FilterParameter.NAME_SORT: {
                    FilterParameterValidator.validateSortType(value);
                    sortingParams.put(key, value);
                    requestParamsCopy.remove(key);
                    break;
                }
            }
        }

        if (!requestParamsCopy.isEmpty()) {
            throw new IncorrectParameterException(IncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_FILTER_PARAMETER);
        }
        filterParams.putAll(sortingParams);
        return filterParams;
    }
}
