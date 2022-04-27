package com.piatnitsa.controller;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * This class is an endpoint of the API which allows to perform CRUD operations on {@link GiftCertificate}.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/certificates".
 * So that {@code GiftCertificateController} is accessed by sending request to /certificates.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Returns a {@link GiftCertificate} by its ID from data source.
     * @param id a {@link GiftCertificate} ID.
     * @return a {@link GiftCertificate} entity.
     * @throws DaoException if {@link GiftCertificate} entity with such ID not found.
     * @throws IncorrectParameterException if specified ID is not valid.
     */
    @GetMapping("/{id}")
    public GiftCertificate certificateById(@PathVariable long id) throws DaoException, IncorrectParameterException {
       return certificateService.getById(id);
    }

    /**
     * Returns all {@link GiftCertificate} entities from data source.
     * @return a {@link List} of {@link GiftCertificate} entities.
     * @throws DaoException if {@link GiftCertificate} entities not found.
     */
    @GetMapping
    public List<GiftCertificate> allCertificates() throws DaoException {
        return certificateService.getAll();
    }

    /**
     * Creates a new {@link GiftCertificate} entity in data source.
     * @param certificate a new {@link GiftCertificate} entity for saving.
     * @return CREATED HttpStatus.
     * @throws DaoException if error occurred while saving an entity.
     * @throws IncorrectParameterException if the {@link GiftCertificate} entity contains incorrect information.
     */
    @PostMapping
    public ResponseEntity<String> createCertificate(@RequestBody GiftCertificate certificate) throws DaoException, IncorrectParameterException {
        certificateService.insert(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Removes from data source a {@link GiftCertificate} by specified ID.
     * @param id a {@link GiftCertificate} ID.
     * @return NO_CONTENT HttpStatus
     * @throws DaoException if the {@link GiftCertificate} entity with specified id not found.
     * @throws IncorrectParameterException if specified ID is not valid.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCertificateById(@PathVariable long id) throws DaoException, IncorrectParameterException {
        certificateService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates a {@link GiftCertificate} by specified ID.
     * @param id a {@link GiftCertificate} ID.
     * @param certificate a {@link GiftCertificate} that contains information for updating.
     * @return CREATED HttpStatus
     * @throws DaoException if the {@link GiftCertificate} entity with specified id not found.
     * @throws IncorrectParameterException if the {@link GiftCertificate} entity contains incorrect information.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificate certificate) throws DaoException, IncorrectParameterException {
        certificateService.update(id, certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Returns a {@link List} of {@link GiftCertificate} from data source by special filter.
     * @param params request parameters which include the information needed for the search.
     * @return a {@link List} of found {@link GiftCertificate} entities.
     * @throws DaoException if {@link GiftCertificate} entities not found.
     * @throws IncorrectParameterException if request parameters contains incorrect parameter values.
     */
    @GetMapping("/filter")
    public List<GiftCertificate> certificateByFilter(@RequestParam Map<String, String> params) throws DaoException, IncorrectParameterException {
        return certificateService.doFilter(params);
    }
}
