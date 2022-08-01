/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Company;
import com.portfolio.APIRest.repository.CompanyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    
    private final CompanyRepository companyRepository;
    
    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    
    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }
    
    @Override
    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
    
    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }
    
    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
    
}
