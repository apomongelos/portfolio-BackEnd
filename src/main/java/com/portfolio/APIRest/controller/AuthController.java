/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.LoginDTO;
import com.portfolio.APIRest.dto.RegisterDTO;
import com.portfolio.APIRest.dto.UserTokenDTO;
import com.portfolio.APIRest.exception.UnauthorizedException;
import com.portfolio.APIRest.exception.UserAlreadyExistRegistrationException;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.TokenService;
import com.portfolio.APIRest.service.UserService;
import java.time.LocalDate;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/auth")
public class AuthController {

//    private static final Logger logger = Logger.getLogger(AuthController.class);
//    private final AuthService authService;
//    private final JwtTokenProvider tokenProvider;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TokenService tokenService;
    
    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper, TokenService tokenService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserTokenDTO> registerUser(@Valid @RequestBody RegisterDTO newRegisterDTO) {
        
        Boolean emailExists = userService.userExistsByEmail(newRegisterDTO.getEmail());
        if (emailExists) {
            throw new UserAlreadyExistRegistrationException("Email already taken");
        }
        
        User newUser = modelMapper.map(newRegisterDTO, User.class);
        newUser.setDateOfBirth(LocalDate.parse(newRegisterDTO.getDateOfBirth()));
//        cast from dob dto of dob usr
        User newUserCreated = userService.createUser(newUser);
        
        Map<String, String> newTokenCreated = tokenService.createToken(newUserCreated);
        UserTokenDTO newUserDTO = new UserTokenDTO();
        newUserDTO.setUserId(newUserCreated.getId());
        newUserDTO.setEmail(newUserCreated.getEmail());
        newUserDTO.setFullName(newUserCreated.getFirstName() + " " + newUserCreated.getLastName());
        newUserDTO.setToken(newTokenCreated);
        return ResponseEntity.ok().body(newUserDTO);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> loginUser(@Valid @RequestBody LoginDTO newLoginDTO) {
        User usrExist = userService.getUserByEmail(newLoginDTO.getEmail());
        if (usrExist == null) {
            throw new UnauthorizedException("email or password incorrect");
        }
        
        Boolean isMatchPasswords = userService.currentPasswordMatches(usrExist, newLoginDTO.getPassword());
        if (!isMatchPasswords) {
            throw new UnauthorizedException("email or password incorrect");
        }
        
        UserTokenDTO newUserDTO = new UserTokenDTO();
        Map<String, String> newTokenCreated = tokenService.createToken(usrExist);
        newUserDTO.setUserId(usrExist.getId());
        newUserDTO.setEmail(usrExist.getEmail());
        newUserDTO.setFullName(usrExist.getFirstName() + " " + usrExist.getLastName());
        newUserDTO.setToken(newTokenCreated);
        return ResponseEntity.ok().body(newUserDTO);
    }

//    @GetMapping("/checkEmailInUse")
//    public ResponseEntity checkEmailInUse(@RequestParam("email") String email) {
//        Boolean emailExists = authService.emailAlreadyExists(email);
//        return ResponseEntity.ok(new ApiResponse(true, emailExists.toString()));
//    }
//
//    @GetMapping("/checkUsernameInUse")
//    public ResponseEntity checkUsernameInUse(@RequestParam(
//            "username") String username) {
//        Boolean usernameExists = authService.usernameAlreadyExists(username);
//        return ResponseEntity.ok(new ApiResponse(true, usernameExists.toString()));
//    }
//    @PostMapping("/log")
//    public ResponseEntity authenticateUser(@RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authService.authenticateUser(loginRequest)
//                .orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));
//
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        logger.info("Logged in User returned [API]: " + customUserDetails.getUsername());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
//                .map(RefreshToken::getToken)
//                .map(refreshToken -> {
//                    String jwtToken = authService.generateToken(customUserDetails);
//                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken, tokenProvider.getExpiryDuration()));
//                })
//                .orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
//    }
}
