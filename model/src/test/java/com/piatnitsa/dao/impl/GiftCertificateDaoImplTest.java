package com.piatnitsa.dao.impl;

import com.piatnitsa.config.H2DatabaseConfig;
import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.SortingParameter;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.GiftCertificateColumn;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.entity.TagColumn;
import com.piatnitsa.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.security.cert.Certificate;
import java.util.*;

@ContextConfiguration(classes = H2DatabaseConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;
    private static final long EXISTED_ID = 1;
    private static final long NOT_EXISTED_ID = 999;
    private static final String CORRECT_CERTIFICATE_NAME = "certificate";
    private static final String CORRECT_DESCRIPTION = "description";
    private static final String CORRECT_TAG_NAME = "tagName";
    private static final String INCORRECT_PARAMETER_VALUE = "incorrect parameter value";
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

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

    @Test
    void getByIdTest_CorrectId() throws DaoException {
        GiftCertificate actual = giftCertificateDao.getById(EXISTED_ID);
        Assertions.assertEquals(GIFT_CERTIFICATE_1, actual);
    }

    @Test
    void getByIdTest_NotExistedId() {
        Assertions.assertThrows(DaoException.class, () -> giftCertificateDao.getById(NOT_EXISTED_ID));
    }

    @Test
    void getAllTest_ExistedRows() throws DaoException {
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        List<GiftCertificate> actual = giftCertificateDao.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql({"classpath:deleteAllRowsFromCertificate.sql"})
    @Sql(scripts = {"classpath:fillingCertificateTable.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllTest_NoRowsInTable() {
        Assertions.assertThrows(DaoException.class, () -> giftCertificateDao.getAll());
    }

    @Test
    void getWithFiltersTest_CorrectParams() throws DaoException {
        Map<String, String> filterParams = new LinkedHashMap<>();
        filterParams.put(GiftCertificateColumn.NAME, CORRECT_CERTIFICATE_NAME);
        filterParams.put(GiftCertificateColumn.DESCRIPTION, CORRECT_DESCRIPTION);
        filterParams.put(TagColumn.TAG_NAME, CORRECT_TAG_NAME);
        filterParams.put(SortingParameter.NAME_SORT_PARAMETER, ASCENDING);
        filterParams.put(SortingParameter.DATE_SORT_PARAMETER, DESCENDING);

        List<GiftCertificate> actual = giftCertificateDao.getWithFilter(filterParams);
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWithFiltersTest_IncorrectParam() {
        Map<String, String> filterParams = new LinkedHashMap<>();
        filterParams.put(GiftCertificateColumn.NAME, CORRECT_CERTIFICATE_NAME);
        filterParams.put(GiftCertificateColumn.DESCRIPTION, CORRECT_DESCRIPTION);
        filterParams.put(SortingParameter.DATE_SORT_PARAMETER, DESCENDING);
        filterParams.put("incorrect_param", INCORRECT_PARAMETER_VALUE);
        Assertions.assertThrows(DaoException.class, () -> giftCertificateDao.getWithFilter(filterParams));
    }

}
