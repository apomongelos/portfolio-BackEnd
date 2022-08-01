///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.portfolio.APIRest.security;
//
//import com.portfolio.APIRest.service.CustomUserDetailsService;
//import java.io.IOException;
//import java.util.List;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// *
// * @author Usuario
// */
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final Logger log = Logger.getLogger(JwtAuthenticationFilter.class);
//
//    @Value("${app.jwt.header}")
//    private String tokenRequestHeader;
//
//    @Value("${app.jwt.header.prefix}")
//    private String tokenRequestHeaderPrefix;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private JwtTokenValidator jwtTokenValidator;
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//            FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String jwt = getJwtFromRequest(request);
//
//            if (StringUtils.hasText(jwt) && jwtTokenValidator.validateToken(jwt)) {
//                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
//                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
//                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesFromJWT(jwt);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, jwt, authorities);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception ex) {
//            log.error("Failed to set user authentication in security context: ", ex);
//            throw ex;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    /**
//     * Extract the token from the Authorization request header
//     */
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader(tokenRequestHeader);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
//            log.info("Extracted Token: " + bearerToken);
//            return bearerToken.replace(tokenRequestHeaderPrefix, "");
//        }
//        return null;
//    }
//}
