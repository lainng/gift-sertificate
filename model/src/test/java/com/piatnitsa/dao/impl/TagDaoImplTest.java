package com.piatnitsa.dao.impl;

import com.piatnitsa.config.H2DatabaseConfig;
import com.piatnitsa.dao.SortingParameter;
import com.piatnitsa.dao.TagDao;
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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = H2DatabaseConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TagDaoImplTest {
    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName3");
    private static final Tag TAG_3 = new Tag(3, "tagName2");
    private static final Tag TAG_4 = new Tag(4, "tagName5");
    private static final Tag TAG_5 = new Tag(5, "tagName4");
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String PART_OF_TAG_NAME = "tagna";
    private static final long NOT_EXISTED_ID = 6;
    private static final String INCORRECT_PARAMETER_VALUE = "incorrect parameter value";
    private static final String ASCENDING = "ASC";

    @Autowired
    private TagDao tagDao;

    @Test
    void getByIdTest_CorrectId() throws DaoException {
        Tag actual = tagDao.getById(TAG_1.getId());
        Assertions.assertEquals(TAG_1, actual);
    }

    @Test
    void getByIdTest_NotExistedId() {
        Assertions.assertThrows(DaoException.class, () -> tagDao.getById(NOT_EXISTED_ID));
    }

    @Test
    void getAllTest_ExistedRows() throws DaoException {
        List<Tag> expectedTags = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        List<Tag> actualTags = tagDao.getAll();
        Assertions.assertEquals(expectedTags, actualTags);
    }

    @Test
    @Sql({"classpath:deleteAllRowsFromTag.sql"})
    @Sql(scripts = {"classpath:fillingTagTable.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllTest_NoRowsInTable() {
        Assertions.assertThrows(DaoException.class, () -> tagDao.getAll());
    }

    @Test
    void getByNameTest_CorrectName() throws DaoException {
        Tag actual = tagDao.getByName(TAG_2.getName());
        Assertions.assertEquals(TAG_2, actual);
    }

    @Test
    void getByNameTest_NotExistedName() {
        Assertions.assertThrows(DaoException.class, () -> tagDao.getByName(NOT_EXISTED_NAME));
    }

    @Test
    void getWithFiltersTest_CorrectParams() throws DaoException {
        Map<String, String> filterParams = new LinkedHashMap<>();
        filterParams.put(TagColumn.TAG_NAME, PART_OF_TAG_NAME);
        filterParams.put(SortingParameter.TAG_NAME_SORT_PARAMETER, ASCENDING);
        List<Tag> actual = tagDao.getWithFilter(filterParams);
        List<Tag> expected = Arrays.asList(TAG_1, TAG_3, TAG_2, TAG_5, TAG_4);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getWithFiltersTest_IncorrectParam() {
        Map<String, String> filterParams = new LinkedHashMap<>();
        filterParams.put(TagColumn.TAG_NAME, PART_OF_TAG_NAME);
        filterParams.put("incorrect_param", INCORRECT_PARAMETER_VALUE);
        Assertions.assertThrows(DaoException.class, () -> tagDao.getWithFilter(filterParams));
    }

}
