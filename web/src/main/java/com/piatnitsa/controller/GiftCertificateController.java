package com.piatnitsa.controller;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.service.GiftCertificateService;
import com.piatnitsa.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/{id}")
    public GiftCertificate certificateById(@PathVariable long id) throws DaoException {
       return certificateService.getById(id);
    }

    @GetMapping
    public List<GiftCertificate> allCertificates() {
        return certificateService.getAll();
    }

    @PostMapping
    public ResponseEntity<String> createCertificate(@RequestBody GiftCertificate certificate) throws DaoException {
        certificateService.insert(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCertificateById(@PathVariable long id) throws DaoException {
        certificateService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificate certificate) throws DaoException {
        certificateService.update(id, certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/filter")
    public List<GiftCertificate> certificateByFilter(@RequestParam Map<String, String> params) throws DaoException, IncorrectParameterException {
        return certificateService.doFilter(params);
    }
}
