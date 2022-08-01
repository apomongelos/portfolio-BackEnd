/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.SocialMediaAccount;
import com.portfolio.APIRest.repository.SocialMediaAccountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class SocialMediaAccountServiceImpl implements SocialMediaAccountService {

    private final SocialMediaAccountRepository socialMediaAccountRepository;

    @Autowired
    public SocialMediaAccountServiceImpl(SocialMediaAccountRepository socialMediaAccountRepository) {
        this.socialMediaAccountRepository = socialMediaAccountRepository;
    }

    @Override
    public List<SocialMediaAccount> getSocialMediaAccounts() {
        return socialMediaAccountRepository.findAll();
    }

    @Override
    public SocialMediaAccount getSocialMediaAccount(Long id) {
        return socialMediaAccountRepository.findById(id).orElse(null);
    }

    @Override
    public SocialMediaAccount saveSocialMediaAccount(SocialMediaAccount socialMediaAccount) {
        return socialMediaAccountRepository.save(socialMediaAccount);
    }

    @Override
    public void deleteSocialMediaAccount(Long id) {
        socialMediaAccountRepository.deleteById(id);
    }

}
