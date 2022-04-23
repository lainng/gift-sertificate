package com.piatnitsa.service.impl;

import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.service.FilterParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateImplTest {
    @Mock
    private GiftCertificateDao certificateDao;

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private GiftCertificateServiceImpl certificateService;

    private static final long NOT_EXISTED_ID = -1;
    private static final long EXISTED_ID = 4;
    private static final String CORRECT_CERTIFICATE_NAME = "certificate";
    private static final String CORRECT_DESCRIPTION = "description";
    private static final String CORRECT_TAG_NAME = "tagName";
    private static final String ASCENDING = "ASC";
    private static final String INCORRECT_SORT_PARAMETER = "not asc";
    private static final String INCORRECT_FILTER_PARAMETER = "Incorrect filter parameter";
    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "giftCertificate1",
            "description1", new BigDecimal("99.90"), 1, "2020-10-20T07:20:15.156",
            "2020-10-20T07:20:15.156",
            Arrays.asList(new Tag(2, "tagName3"), new Tag(1, "tagName1")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3",
            "description3", new BigDecimal("100.99"), 3, "2019-10-20T07:20:15.156",
            "2019-10-20T07:20:15.156",
            Collections.singletonList(new Tag(2, "tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "giftCertificate2",
            "description2", new BigDecimal("999.99"), 2, "2018-10-20T07:20:15.156",
            "2018-10-20T07:20:15.156",
            Collections.emptyList());
    private static final GiftCertificate NEW_CORRECT_CERTIFICATE = new GiftCertificate(0, "newGiftCertificate",
            "newDescription", new BigDecimal("9999.99"), 365, "2022-04-23T07:20:15.156",
            "2022-04-23T07:20:15.156",
            Collections.singletonList(new Tag("newTagName")));
    private static final GiftCertificate INCORRECT_CERTIFICATE = new GiftCertificate(0, "n",
            "", new BigDecimal("9999.9999"), 400, "2022-04-23T07:20:15.156",
            "2022-04-23T07:20:15.156",
            Collections.singletonList(new Tag("")));

    @Test
    void getByIdTest_ExistedId() throws DaoException, IncorrectParameterException {
        Mockito.when(certificateDao.getById(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);
        GiftCertificate actual = certificateService.getById(GIFT_CERTIFICATE_1.getId());
        Assertions.assertEquals(GIFT_CERTIFICATE_1, actual);
    }

    @Test
    void getByIdTest_Not_ExistedId() {
        Assertions.assertThrows(IncorrectParameterException.class, () -> certificateService.getById(NOT_EXISTED_ID));
    }

    @Test
    void getAllTest() throws DaoException {
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Mockito.when(certificateDao.getAll()).thenReturn(expected);
        List<GiftCertificate> actual = certificateService.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void removeByIdTest_ExistedId() {
        Assertions.assertDoesNotThrow(() -> certificateService.removeById(GIFT_CERTIFICATE_2.getId()));
    }

    @Test
    void removeByIdTest_NotExistedId() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> certificateService.removeById(NOT_EXISTED_ID));
    }

    @Test
    void insertTest_CorrectCertificateParam() {
        Assertions.assertDoesNotThrow(() -> certificateService.insert(NEW_CORRECT_CERTIFICATE));
    }

    @Test
    void insertTest_IncorrectCertificateParam() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> certificateService.insert(INCORRECT_CERTIFICATE));
    }

    @Test
    void updateTest_CorrectCertificateParams() {
        Assertions.assertDoesNotThrow(() -> certificateService.update(EXISTED_ID, NEW_CORRECT_CERTIFICATE));
    }

    @Test
    void updateTest_IncorrectCertificateParam() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> certificateService.update(EXISTED_ID, INCORRECT_CERTIFICATE));
    }

    @Test
    void updateTest_IncorrectCertificateId() {
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> certificateService.update(NOT_EXISTED_ID, NEW_CORRECT_CERTIFICATE));
    }

    @Test
    void doFilterTest_CorrectParams() throws IncorrectParameterException, DaoException {
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1);
        Mockito.when(certificateDao.getWithFilter(Mockito.anyMap())).thenReturn(expected);

        Map<String, String> params = new LinkedHashMap<>();
        params.put(FilterParameter.NAME, CORRECT_CERTIFICATE_NAME);
        params.put(FilterParameter.DATE_SORT, ASCENDING);
        params.put(FilterParameter.DESCRIPTION, CORRECT_DESCRIPTION);

        List<GiftCertificate> actual = certificateService.doFilter(params);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void doFilterTest_IncorrectSortParam() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(FilterParameter.NAME, CORRECT_CERTIFICATE_NAME);
        params.put(FilterParameter.NAME_SORT, INCORRECT_SORT_PARAMETER);
        params.put(FilterParameter.TAG_NAME, CORRECT_TAG_NAME);
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> certificateService.doFilter(params));
    }

    @Test
    void doFilterTest_IncorrectParam() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(FilterParameter.NAME, CORRECT_CERTIFICATE_NAME);
        params.put(FilterParameter.NAME_SORT, ASCENDING);
        params.put(INCORRECT_FILTER_PARAMETER, GIFT_CERTIFICATE_3.getCreateDate());
        Assertions.assertThrows(IncorrectParameterException.class,
                () -> certificateService.doFilter(params));
    }
}
