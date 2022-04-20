package com.piatnitsa.dao;

import com.piatnitsa.entity.GiftCertificateColumn;
import com.piatnitsa.entity.TagColumn;
import com.piatnitsa.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.piatnitsa.dao.SortingParameter.*;

public class QueryBuilderTest {
    private final QueryBuilder queryBuilder = new QueryBuilder();
    private static final String FILTER_TAG_QUERY = "select * from tag ";
    private static final String FILTER_CERTIFICATE_QUERY = "select * from gift_certificate gc left join gift_certificate_with_tags gcwt on gc.id = gcwt.gift_certificate_id left join tag t on t.id = gcwt.tag_id ";
    private static final String UPDATE_CERTIFICATE_QUERY = "update gift_certificate set ";
    private static final String CORRECT_TAG_NAME = "tagName";
    private static final String CORRECT_CERTIFICATE_NAME = "giftCertificate";
    private static final String CORRECT_DESCRIPTION = "description";
    private static final String UPDATABLE_ID = "1";
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "DESC";
    private static final String INCORRECT_FILTER_PARAMETER = "incorrect parameter";

    @Test
    void buildQueryWithFiltersTest_CorrectTagParams() throws DaoException {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(TagColumn.TAG_NAME, CORRECT_TAG_NAME);
        parameters.put(TAG_NAME_SORT_PARAMETER, ASCENDING);

        StringBuilder sb = new StringBuilder(FILTER_TAG_QUERY);
        sb.append(" where ")
                .append(TagColumn.TAG_NAME).append(" ilike '%").append(CORRECT_TAG_NAME).append("%' ")
                .append(" order by ").append(TagColumn.TAG_NAME).append(" ").append(ASCENDING);
        String expected = sb.toString();
        String actual = queryBuilder.buildQueryWithFilters(FILTER_TAG_QUERY, parameters);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void buildQueryWithFiltersTest_WithIncorrectParam() {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(TagColumn.TAG_NAME, CORRECT_TAG_NAME);
        parameters.put(TAG_NAME_SORT_PARAMETER, ASCENDING);
        parameters.put(INCORRECT_FILTER_PARAMETER, null);

        Assertions.assertThrows(DaoException.class, () -> queryBuilder.buildQueryWithFilters(FILTER_TAG_QUERY, parameters));
    }

    @Test
    void buildQueryWithFiltersTest_CorrectCertificateParams() throws DaoException {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(GiftCertificateColumn.NAME, CORRECT_CERTIFICATE_NAME);
        parameters.put(GiftCertificateColumn.DESCRIPTION, CORRECT_DESCRIPTION);
        parameters.put(NAME_SORT_PARAMETER, ASCENDING);
        parameters.put(DATE_SORT_PARAMETER, DESCENDING);

        StringBuilder sb = new StringBuilder(FILTER_CERTIFICATE_QUERY);
        sb.append(" where ")
                .append(GiftCertificateColumn.NAME).append(" ilike '%").append(CORRECT_CERTIFICATE_NAME).append("%' ")
                .append(" and ")
                .append(GiftCertificateColumn.DESCRIPTION).append(" ilike '%").append(CORRECT_DESCRIPTION).append("%' ")
                .append(" order by ").append(GiftCertificateColumn.NAME).append(" ").append(ASCENDING)
                .append(", ")
                .append(GiftCertificateColumn.CREATE_DATE).append(" ").append(DESCENDING);
        String expected = sb.toString();
        String actual = queryBuilder.buildQueryWithFilters(FILTER_CERTIFICATE_QUERY, parameters);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void buildUpdateQueryTest() {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put(GiftCertificateColumn.NAME, CORRECT_CERTIFICATE_NAME);
        parameters.put(GiftCertificateColumn.DESCRIPTION, CORRECT_DESCRIPTION);
        parameters.put(GiftCertificateColumn.ID, UPDATABLE_ID);

        StringBuilder sb = new StringBuilder(UPDATE_CERTIFICATE_QUERY);

        sb.append(GiftCertificateColumn.NAME).append("='").append(CORRECT_CERTIFICATE_NAME).append("', ")
                .append(GiftCertificateColumn.DESCRIPTION).append("='").append(CORRECT_DESCRIPTION).append("' ")
                .append("where id=").append(UPDATABLE_ID);

        String expected = sb.toString();
        String actual = queryBuilder.buildUpdateQuery(UPDATE_CERTIFICATE_QUERY, parameters);
        Assertions.assertEquals(expected, actual);
    }
}
