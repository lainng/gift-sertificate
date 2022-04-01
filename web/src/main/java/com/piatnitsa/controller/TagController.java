package com.piatnitsa.controller;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.service.impl.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> allTags() throws DaoException {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag tagById(@PathVariable long id) throws DaoException {
        return tagService.getById(id);
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Tag tag) throws DaoException {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) throws DaoException {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
