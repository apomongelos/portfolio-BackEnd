/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Certification;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface CertificationService {

    public List<Certification> getCertifications();

    public Certification getCertification(Long id);

    public Certification saveCertification(Certification certification);

    public void deleteCertification(Long id);
}
