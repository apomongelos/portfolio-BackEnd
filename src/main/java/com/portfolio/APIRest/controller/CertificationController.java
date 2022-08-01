/*
 * To change this license header, choose License Headers in Certification Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.CertificationCreateDTO;
import com.portfolio.APIRest.dto.CertificationDTO;
import com.portfolio.APIRest.dto.CertificationUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.Certification;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.CertificationService;
import com.portfolio.APIRest.service.UserService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/certifications")
public class CertificationController {

    private final CertificationService certificationService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CertificationController(CertificationService certificationService, ModelMapper modelMapper, UserService userService) {
        this.certificationService = certificationService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<CertificationDTO>> getCertifications() {
        List<Certification> certifications = certificationService.getCertifications();

        List<CertificationDTO> certificationsDTOs = certifications
                .stream()
                .map(certification -> modelMapper.map(certification, CertificationDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(certificationsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationDTO> getCertification(@PathVariable Long id) {
        Certification certification = certificationService.getCertification(id);
        if (certification == null) {
            throw new NotFoundException("not found certification with id: " + id);
        }
        CertificationDTO certificationDTO = modelMapper.map(certification, CertificationDTO.class);
        return ResponseEntity.ok().body(certificationDTO);
    }

    @PostMapping
    public ResponseEntity<CertificationDTO> addNewCertification(@Valid @RequestBody CertificationCreateDTO certificationDTO) {
        User usrExist = userService.getUser(certificationDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + certificationDTO.getUserId());
        }

        Certification newCertification = new Certification();
        newCertification.setName(certificationDTO.getName());
        newCertification.setStartDate(LocalDate.parse(certificationDTO.getStartDate()));

        newCertification.setUser(usrExist);

        if (certificationDTO.getUrl() != null) {
            newCertification.setUrl(certificationDTO.getUrl());
        }
        if (certificationDTO.getEndDate() != null) {
            newCertification.setEndDate(LocalDate.parse(certificationDTO.getEndDate()));
        }

        Certification newCertificationCreated = certificationService.saveCertification(newCertification);
        CertificationDTO newCertificationDTO = modelMapper.map(newCertificationCreated, CertificationDTO.class);
        return new ResponseEntity<>(newCertificationDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationDTO> updateCertification(@PathVariable Long id, @Valid @RequestBody CertificationUpdateDTO certificationDTO) {

        Certification certToUpdate = certificationService.getCertification(id);

        if (certToUpdate == null) {
            throw new NotFoundException("not found certification with id");
        }

        if (certificationDTO.getUserId() != null) {
            User usrExist = userService.getUser(certificationDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + certificationDTO.getUserId());
            }
            certToUpdate.setUser(usrExist);
        }

        if (certificationDTO.getName() != null) {
            certToUpdate.setName(certificationDTO.getName());
        }
        if (certificationDTO.getStartDate() != null) {
            certToUpdate.setStartDate(LocalDate.parse(certificationDTO.getStartDate()));
        }
        if (certificationDTO.getUrl() != null) {
            certToUpdate.setUrl(certificationDTO.getUrl());
        }
        if (certificationDTO.getEndDate() != null) {
            certToUpdate.setEndDate(LocalDate.parse(certificationDTO.getEndDate()));
        }

        Certification certificationUpdated = certificationService.saveCertification(certToUpdate);
        CertificationDTO newCertificationDTO = modelMapper.map(certificationUpdated, CertificationDTO.class);
        return new ResponseEntity<>(newCertificationDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertification(@PathVariable Long id) {
        try {
            certificationService.deleteCertification(id);
        } catch (Exception e) {
            throw new NotFoundException("not found certification with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully certification with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
