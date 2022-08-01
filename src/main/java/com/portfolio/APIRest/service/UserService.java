/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.User;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface UserService {

    public List<User> getUsers();

    public User getUser(Long id);

    public User getUserByEmail(String email);

    public User getUserByFirstNameAndLastName(String firstName, String lastName);

    public Boolean userExistsByEmail(String email);

    public Boolean userExistsByUsername(String username);

    public User createUser(User user);

    public User saveUser(User user);

    public Boolean currentPasswordMatches(User currentUser, String password);
}
