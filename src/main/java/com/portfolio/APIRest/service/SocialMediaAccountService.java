/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.SocialMediaAccount;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface SocialMediaAccountService {

    public List<SocialMediaAccount> getSocialMediaAccounts();

    public SocialMediaAccount getSocialMediaAccount(Long id);

    public SocialMediaAccount saveSocialMediaAccount(SocialMediaAccount socialMediaAccount);

    public void deleteSocialMediaAccount(Long id);
}
