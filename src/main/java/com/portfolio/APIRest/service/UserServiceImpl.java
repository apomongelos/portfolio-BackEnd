/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.model.Role;
import com.portfolio.APIRest.model.CustomUserDetails;
import com.portfolio.APIRest.model.payload.RegistrationRequest;
import com.portfolio.APIRest.repository.UserRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(User newUser1) {
        User newUser = new User();
        Boolean isNewUserAsAdmin = false;
        // here we could use model mapper
        newUser.setEmail(newUser1.getEmail());
        newUser.setPassword(passwordEncoder.encode(newUser1.getPassword()));
        newUser.addRoles(getRolesForNewUser(isNewUserAsAdmin));
        newUser.setIsActive(true);
        newUser.setIsEmailVerified(false);

        newUser.setFirstName(newUser1.getFirstName());
        newUser.setLastName(newUser1.getLastName());
        newUser.setUsername(newUser1.getUsername());
        newUser.setAboutMe(newUser1.getAboutMe());
        // its necesary to add date of birth in body request
        newUser.setDateOfBirth(newUser1.getDateOfBirth());
        return userRepository.save(newUser);
    }

    private Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin) {
        Set<Role> newUserRoles = new HashSet<>(roleService.findAll());
        if (!isToBeMadeAdmin) {
            newUserRoles.removeIf(Role::isAdminRole);
        }
        //logger.info("Setting user roles: " + newUserRoles);
        return newUserRoles;
    }

//    public void logoutUser(@CurrentUser CustomUserDetails currentUser, LogOutRequest logOutRequest) {
//        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
//        UserDevice userDevice = userDeviceService.findDeviceByUserId(currentUser.getId(), deviceId)
//                .filter(device -> device.getDeviceId().equals(deviceId))
//                .orElseThrow(() -> new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(), "Invalid device Id supplied. No matching device found for the given user "));
//
//        logger.info("Removing refresh token associated with device [" + userDevice + "]");
//        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
//    }      
    @Override
    public Boolean currentPasswordMatches(User currentUser, String password) {
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

    @Override
    public User getUserByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
    }
}
