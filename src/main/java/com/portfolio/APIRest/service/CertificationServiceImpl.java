/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Certification;
import com.portfolio.APIRest.repository.CertificationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class CertificationServiceImpl implements CertificationService {
    
    private final CertificationRepository certificationRepository;
    
    @Autowired
    public CertificationServiceImpl(CertificationRepository certificationRepository) {
        this.certificationRepository = certificationRepository;
    }
    
    @Override
    public List<Certification> getCertifications() {
        return certificationRepository.findAll();
    }
    
    @Override
    public Certification getCertification(Long id) {
        return certificationRepository.findById(id).orElse(null);
    }
    
    @Override
    public Certification saveCertification(Certification certification) {
        return certificationRepository.save(certification);
    }
    
    @Override
    public void deleteCertification(Long id) {
        certificationRepository.deleteById(id);
    }
    
}
