/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.service;

import com.portfolio.APIRest.exception.ResourceAlreadyInUseException;
//import com.portfolio.APIRest.security.JwtTokenProvider;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.model.CustomUserDetails;
import com.portfolio.APIRest.model.payload.LoginRequest;
import com.portfolio.APIRest.model.payload.RegistrationRequest;
//import com.portfolio.APIRest.model.token.RefreshToken;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class);
    private final UserService userService;
//    private final JwtTokenProvider tokenProvider;
//    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final EmailVerificationTokenService emailVerificationTokenService;
//    private final UserDeviceService userDeviceService;
//    private final PasswordResetTokenService passwordResetService;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
//        this.tokenProvider = tokenProvider;
//        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
    }

//    public Optional<User> registerUser(RegistrationRequest newRegistrationRequest) {
//        String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
//        if (emailAlreadyExists(newRegistrationRequestEmail)) {
//            logger.error("Email already exists: " + newRegistrationRequestEmail);
//            throw new ResourceAlreadyInUseException("Email", "Address", newRegistrationRequestEmail);
//        }
//        logger.info("Trying to register new user [" + newRegistrationRequestEmail + "]");
////        User newUser = userService.createUser(newRegistrationRequest);
////        User registeredNewUser = userService.save(newUser);
//        return Optional.ofNullable(registeredNewUser);
//    }

    public Boolean emailAlreadyExists(String email) {
        return userService.userExistsByEmail(email);
    }

//    public Boolean usernameAlreadyExists(String username) {
//        return userService.userExistsByUsername(username);
//    }

//    public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
//        return Optional.ofNullable(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
//                loginRequest.getPassword())));
//    }

//    public Optional<User> confirmEmailRegistration(String emailToken) {
//        EmailVerificationToken emailVerificationToken = emailVerificationTokenService.findByToken(emailToken)
//                .orElseThrow(() -> new ResourceNotFoundException("Token", "Email verification", emailToken));
//
//        User registeredUser = emailVerificationToken.getUser();
//        if (registeredUser.getEmailVerified()) {
//            logger.info("User [" + emailToken + "] already registered.");
//            return Optional.of(registeredUser);
//        }
//
//        emailVerificationTokenService.verifyExpiration(emailVerificationToken);
//        emailVerificationToken.setConfirmedStatus();
//        emailVerificationTokenService.save(emailVerificationToken);
//
//        registeredUser.markVerificationConfirmed();
//        userService.save(registeredUser);
//        return Optional.of(registeredUser);
//    }
//    public Optional<EmailVerificationToken> recreateRegistrationToken(String existingToken) {
//        EmailVerificationToken emailVerificationToken = emailVerificationTokenService.findByToken(existingToken)
//                .orElseThrow(() -> new ResourceNotFoundException("Token", "Existing email verification", existingToken));
//
//        if (emailVerificationToken.getUser().getEmailVerified()) {
//            return Optional.empty();
//        }
//        return Optional.ofNullable(emailVerificationTokenService.updateExistingTokenWithNameAndExpiry(emailVerificationToken));
//    }
    /**
     * Validates the password of the current logged in user with the given
     * password
     */
    private Boolean currentPasswordMatches(User currentUser, String password) {
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

//    /**
//     * Updates the password of the current logged in user
//     */
//    public Optional<User> updatePassword(CustomUserDetails customUserDetails,
//            UpdatePasswordRequest updatePasswordRequest) {
//        String email = customUserDetails.getEmail();
//        User currentUser = userService.findByEmail(email)
//                .orElseThrow(() -> new UpdatePasswordException(email, "No matching user found"));
//
//        if (!currentPasswordMatches(currentUser, updatePasswordRequest.getOldPassword())) {
//            logger.info("Current password is invalid for [" + currentUser.getPassword() + "]");
//            throw new UpdatePasswordException(currentUser.getEmail(), "Invalid current password");
//        }
//        String newPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
//        currentUser.setPassword(newPassword);
//        userService.save(currentUser);
//        return Optional.of(currentUser);
//    }
//    public String generateToken(CustomUserDetails customUserDetails) {
//        return tokenProvider.generateToken(customUserDetails);
//    }
//
//    private String generateTokenFromUserId(Long userId) {
//        return tokenProvider.generateTokenFromUserId(userId);
//    }

//    public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication, LoginRequest loginRequest) {
//        User currentUser = (User) authentication.getPrincipal();
////        String deviceId = loginRequest.getDeviceInfo().getDeviceId();
////        userDeviceService.findDeviceByUserId(currentUser.getId(), deviceId)
////                .map(UserDevice::getRefreshToken)
////                .map(RefreshToken::getId)
////                .ifPresent(refreshTokenService::deleteById);
//
////        UserDevice userDevice = userDeviceService.createUserDevice(loginRequest.getDeviceInfo());
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken();
////        userDevice.setUser(currentUser);
////        userDevice.setRefreshToken(refreshToken);
////        refreshToken.setUserDevice(userDevice);
//        refreshToken = refreshTokenService.save(refreshToken);
//        return Optional.ofNullable(refreshToken);
//    }
//    public Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
//        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
//
//        return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshToken -> {
//                    refreshTokenService.verifyExpiration(refreshToken);                   
//                    refreshTokenService.increaseCount(refreshToken);
//                    return refreshToken;
//                })
//                .map(RefreshToken::getUserDevice)                
//                .map(CustomUserDetails::new)
//                .map(this::generateToken))
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in database.Please login again"));
//    }
}
