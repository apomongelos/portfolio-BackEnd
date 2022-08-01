/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.UserJobTitle;
import com.portfolio.APIRest.model.UserJobTitleId;
import com.portfolio.APIRest.repository.UserJobTitleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UserJobTitleServiceImpl implements UserJobTitleService {

    private final UserJobTitleRepository userJobTitleRepository;

    @Autowired
    public UserJobTitleServiceImpl(UserJobTitleRepository userJobTitleRepository) {
        this.userJobTitleRepository = userJobTitleRepository;
    }

    @Override
    public List<UserJobTitle> getUserJobTitles() {
        return userJobTitleRepository.findAll();
    }

    @Override
    public UserJobTitle getUserJobTitle(UserJobTitleId userJobTitleId) {
        return userJobTitleRepository.findById(userJobTitleId).orElse(null);
    }

    @Override
    public UserJobTitle saveUserJobTitle(UserJobTitle userJobTitle) {
        return userJobTitleRepository.save(userJobTitle);
    }

    @Override
    public void deleteUserJobTitle(UserJobTitleId userJobTitleId) {
        userJobTitleRepository.deleteById(userJobTitleId);
    }

}
