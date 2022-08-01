/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.UserJobTitle;
import com.portfolio.APIRest.model.UserJobTitleId;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface UserJobTitleService {

    public List<UserJobTitle> getUserJobTitles();

    public UserJobTitle getUserJobTitle(UserJobTitleId userJobTitleId);

    public UserJobTitle saveUserJobTitle(UserJobTitle userJobTitle);

    public void deleteUserJobTitle(UserJobTitleId userJobTitleId);
}
