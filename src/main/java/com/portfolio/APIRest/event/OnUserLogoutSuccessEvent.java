/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.event;

import java.time.Instant;
import java.util.Date;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Usuario
 */
public class OnUserLogoutSuccessEvent extends ApplicationEvent {

    private final String userEmail;
    private final String token;
    private final Date eventTime;

    public OnUserLogoutSuccessEvent(String userEmail, String token) {
        super(userEmail);
        this.userEmail = userEmail;
        this.token = token;
        this.eventTime = Date.from(Instant.now());
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getToken() {
        return token;
    }

    public Date getEventTime() {
        return eventTime;
    }
}
