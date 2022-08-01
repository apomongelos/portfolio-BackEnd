/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.Company;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface CompanyService {

    public List<Company> getCompanies();

    public Company getCompany(Long id);

    public Company saveCompany(Company company);

    public void deleteCompany(Long id);
}
