/*
 * To change this license header, choose License Headers in socialMediaAccount Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.controller;

import com.portfolio.APIRest.dto.SocialMediaAccountCreateDTO;
import com.portfolio.APIRest.dto.SocialMediaAccountDTO;
import com.portfolio.APIRest.dto.SocialMediaAccountUpdateDTO;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.model.SocialMediaAccount;
import com.portfolio.APIRest.model.User;
import com.portfolio.APIRest.service.SocialMediaAccountService;
import com.portfolio.APIRest.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping(path = "api/social_media_accounts")
public class SocialMediaAccountController {

    private final SocialMediaAccountService socialMediaAccountService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public SocialMediaAccountController(SocialMediaAccountService socialMediaAccountService, ModelMapper modelMapper, UserService userService) {
        this.socialMediaAccountService = socialMediaAccountService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<SocialMediaAccountDTO>> getSocialMediaAccounts() {
        List<SocialMediaAccount> accounts = socialMediaAccountService.getSocialMediaAccounts();

        List<SocialMediaAccountDTO> accountDTOs = accounts
                .stream()
                .map(project -> modelMapper.map(project, SocialMediaAccountDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(accountDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialMediaAccountDTO> getSocialMediaAccount(@PathVariable Long id) {
        SocialMediaAccount socialMediaAccount = socialMediaAccountService.getSocialMediaAccount(id);
        if (socialMediaAccount == null) {
            throw new NotFoundException("not found social media account with id: " + id);
        }
        SocialMediaAccountDTO accountDTO = modelMapper.map(socialMediaAccount, SocialMediaAccountDTO.class);
        return ResponseEntity.ok().body(accountDTO);
    }

    @PostMapping
    public ResponseEntity<SocialMediaAccountDTO> addNewSocialMediaAccount(@Valid @RequestBody SocialMediaAccountCreateDTO socialMediaAccountDTO) {
        User usrExist = userService.getUser(socialMediaAccountDTO.getUserId());
        if (usrExist == null) {
            throw new NotFoundException("not found user with id: " + socialMediaAccountDTO.getUserId());
        }
        SocialMediaAccount newSMAccount = new SocialMediaAccount();
        newSMAccount.setName(socialMediaAccountDTO.getName());
        newSMAccount.setUrl(socialMediaAccountDTO.getUrl());
        newSMAccount.setUser(usrExist);

        SocialMediaAccount newSocialMediaAccountCreated = socialMediaAccountService.saveSocialMediaAccount(newSMAccount);
        SocialMediaAccountDTO newSocialMediaAccount = modelMapper.map(newSocialMediaAccountCreated, SocialMediaAccountDTO.class);
        return new ResponseEntity<>(newSocialMediaAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialMediaAccountDTO> updateSocialMediaAccount(@PathVariable Long id, @Valid @RequestBody SocialMediaAccountUpdateDTO socialMediaAccountDTO) {
        SocialMediaAccount socialMediaAccountToUpdate = socialMediaAccountService.getSocialMediaAccount(id);
        if (socialMediaAccountToUpdate == null) {
            throw new NotFoundException("not found social media account with id");
        }

        if (socialMediaAccountDTO.getUserId() != null) {
            User usrExist = userService.getUser(socialMediaAccountDTO.getUserId());
            if (usrExist == null) {
                throw new NotFoundException("not found user with id: " + socialMediaAccountDTO.getUserId());
            }
            socialMediaAccountToUpdate.setUser(usrExist);
        }
        if (socialMediaAccountDTO.getName() != null) {
            socialMediaAccountToUpdate.setName(socialMediaAccountDTO.getName());
        }
        if (socialMediaAccountDTO.getUrl() != null) {
            socialMediaAccountToUpdate.setUrl(socialMediaAccountDTO.getUrl());
        }

        SocialMediaAccount socialMediaAccountUpdated = socialMediaAccountService.saveSocialMediaAccount(socialMediaAccountToUpdate);
        SocialMediaAccountDTO newSocialMediaAccountDTO = modelMapper.map(socialMediaAccountUpdated, SocialMediaAccountDTO.class);
        return new ResponseEntity<>(newSocialMediaAccountDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSocialMediaAccount(@PathVariable Long id) {
        try {
            socialMediaAccountService.deleteSocialMediaAccount(id);
        } catch (Exception e) {
            throw new NotFoundException("not found social media account with id: " + id);
        }

        Map<String, String> response = new HashMap<String, String>() {
            {
                put("message", "deleted succesfully social media account with id: " + id);
            }
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
