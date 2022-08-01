/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.util;

import java.util.UUID;

/**
 *
 * @author Usuario
 */
public class Util {

    private Util() {
        throw new UnsupportedOperationException("Cannot instantiate a Util class");
    }

    public static String generateRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
