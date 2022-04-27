package com.piatnitsa.controller;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * This class is an endpoint of the API which allows to perform CRD operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tags".
 * So that {@code TagController} is accessed by sending request to /tags.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Returns all {@link Tag} entities from data source.
     * @return a {@link List} of {@link Tag} entities.
     * @throws DaoException if {@link Tag} entities not found.
     */
    @GetMapping
    public List<Tag> allTags() throws DaoException {
        return tagService.getAll();
    }

    /**
     * Returns a {@link Tag} by its ID from data source.
     * @param id a {@link Tag} ID.
     * @return a {@link Tag} entity.
     * @throws DaoException if {@link Tag} entity with such ID not found.
     * @throws IncorrectParameterException if specified ID is not valid.
     */
    @GetMapping("/{id}")
    public Tag tagById(@PathVariable long id) throws DaoException, IncorrectParameterException {
        return tagService.getById(id);
    }

    /**
     * Creates a new {@link Tag} entity in data source.
     * @param tag a new {@link Tag} entity for saving.
     * @return CREATED HttpStatus.
     * @throws DaoException if error occurred while saving an entity.
     * @throws IncorrectParameterException if the {@link Tag} entity contains incorrect information.
     */
    @PostMapping
    public ResponseEntity<String> createTag(@RequestBody Tag tag) throws DaoException, IncorrectParameterException {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Removes from data source a {@link Tag} by specified ID.
     * @param id a {@link Tag} ID.
     * @return NO_CONTENT HttpStatus
     * @throws DaoException if the {@link Tag} entity with specified id not found.
     * @throws IncorrectParameterException if specified ID is not valid.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) throws DaoException, IncorrectParameterException {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns a {@link List} of {@link Tag} from data source by special filter.
     * @param params request parameters which include the information needed for the search.
     * @return a {@link List} of found {@link Tag} entities.
     * @throws DaoException if {@link Tag} entities not found.
     * @throws IncorrectParameterException if request parameters contains incorrect parameter values.
     */
    @GetMapping("/filter")
    public List<Tag> tagByFilter(@RequestParam Map<String, String> params) throws DaoException, IncorrectParameterException {
        return tagService.doFilter(params);
    }
}
