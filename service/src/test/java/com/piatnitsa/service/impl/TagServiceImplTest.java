package com.piatnitsa.service.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.impl.TagDaoImpl;
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

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    private static final long NOT_EXISTED_ID = -1;
    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName3");
    private static final Tag TAG_3 = new Tag(3, "tagName2");
    private static final Tag TAG_4 = new Tag(4, "tagName5");
    private static final Tag TAG_5 = new Tag(5, "tagName4");
    private static final Tag NEW_TAG = new Tag("newTagName");
    private static final Tag INCORRECT_TAG = new Tag("n");
    private static final String ASCENDING = "asc";
    private static final String INCORRECT_SORT_PARAMETER = "not asc";

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getById_ExistedId() throws DaoException, IncorrectParameterException {
        Mockito.when(tagDao.getById(TAG_1.getId())).thenReturn(TAG_1);
        Tag actual = tagService.getById(TAG_1.getId());
        Assertions.assertEquals(TAG_1, actual);
    }

    @Test
    void getById_NotExistedId() {
        Assertions.assertThrows(IncorrectParameterException.class, () -> tagService.getById(NOT_EXISTED_ID));
    }

    @Test
    void removeById_ExistedId() {
        Assertions.assertDoesNotThrow(() -> tagService.removeById(TAG_2.getId()));
    }

    @Test
    void removeById_NotExistedId() {
        Assertions.assertThrows(IncorrectParameterException.class, () -> tagService.removeById(NOT_EXISTED_ID));
    }

    @Test
    void insert_CorrectTagParam() {
        Assertions.assertDoesNotThrow(() -> tagService.insert(NEW_TAG));
    }

    @Test
    void insert_IncorrectTagParam() {
        Assertions.assertThrows(IncorrectParameterException.class, () -> tagService.insert(INCORRECT_TAG));
    }

    @Test
    void getAll() throws DaoException {
        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        Mockito.when(tagDao.getAll()).thenReturn(expected);
        List<Tag> actual = tagService.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void doFilter_CorrectParams() throws DaoException, IncorrectParameterException {
        List<Tag> expected = Collections.singletonList(TAG_4);
        Map<String, String> params = new HashMap<>();
        params.put(FilterParameter.SORT_BY_TAG_NAME, ASCENDING);
        params.put(FilterParameter.TAG_NAME, TAG_4.getName());
        Mockito.when(tagDao.getWithFilter(params)).thenReturn(expected);
        List<Tag> actual = tagService.doFilter(params);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void doFilter_IncorrectParams() {
        Map<String, String> params = new HashMap<>();
        params.put(FilterParameter.SORT_BY_TAG_NAME, INCORRECT_SORT_PARAMETER);
        params.put(FilterParameter.TAG_NAME, TAG_5.getName());
        Assertions.assertThrows(IncorrectParameterException.class, () -> tagService.doFilter(params));
    }

}
