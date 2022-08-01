///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.portfolio.APIRest.service;
//
//import com.portfolio.APIRest.exception.TokenRefreshException;
//import com.portfolio.APIRest.model.token.RefreshToken;
//import com.portfolio.APIRest.repository.RefreshTokenRepository;
//import com.portfolio.APIRest.util.Util;
//import java.time.Instant;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author Usuario
// */
//@Service
//public class RefreshTokenService {
//
//    private final RefreshTokenRepository refreshTokenRepository;
//
//    @Value("${app.token.refresh.duration}")
//    private Long refreshTokenDurationMs;
//
//    @Autowired
//    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
//        this.refreshTokenRepository = refreshTokenRepository;
//    }
//
//    /**
//     * Find a refresh token based on the natural id i.e the token itself
//     */
//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }
//
//    /**
//     * Persist the updated refreshToken instance to database
//     */
//    public RefreshToken save(RefreshToken refreshToken) {
//        return refreshTokenRepository.save(refreshToken);
//    }
//
//    /**
//     * Creates and returns a new refresh token
//     */
//    public RefreshToken createRefreshToken() {
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
//        refreshToken.setToken(Util.generateRandomUuid());
//        refreshToken.setRefreshCount(0L);
//        return refreshToken;
//    }
//
//    /**
//     * Verify whether the token provided has expired or not on the basis of the
//     * current server time and/or throw error otherwise
//     */
//    public void verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
//        }
//    }
//
//    /**
//     * Delete the refresh token associated with the user device
//     */
//    public void deleteById(Long id) {
//        refreshTokenRepository.deleteById(id);
//    }
//
//    /**
//     * Increase the count of the token usage in the database. Useful for audit
//     * purposes
//     */
//    public void increaseCount(RefreshToken refreshToken) {
//        refreshToken.incrementRefreshCount();
//        save(refreshToken);
//    }
//}
