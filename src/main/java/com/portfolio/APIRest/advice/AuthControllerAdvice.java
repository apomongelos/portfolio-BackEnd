/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portfolio.APIRest.advice;

import com.portfolio.APIRest.model.payload.ApiResponse;
import com.portfolio.APIRest.exception.AppException;
import com.portfolio.APIRest.exception.BadRequestException;
import com.portfolio.APIRest.exception.InvalidTokenRequestException;
import com.portfolio.APIRest.exception.NotFoundException;
import com.portfolio.APIRest.exception.TokenRefreshException;
import com.portfolio.APIRest.exception.ResourceAlreadyInUseException;
import com.portfolio.APIRest.exception.ResourceNotFoundException;
import com.portfolio.APIRest.exception.UnauthorizedException;
import com.portfolio.APIRest.exception.UserAlreadyExistRegistrationException;
import com.portfolio.APIRest.exception.UserLoginException;
import com.portfolio.APIRest.exception.UserLogoutException;
import com.portfolio.APIRest.exception.UserRegistrationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author Usuario
 */
@RestControllerAdvice
public class AuthControllerAdvice {

    private static final Logger logger = Logger.getLogger(AuthControllerAdvice.class);

    private final MessageSource messageSource;

    @Autowired
    public AuthControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, List<String>> listOfErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            if (listOfErrors.containsKey(fieldName)) {
                listOfErrors.get(fieldName).add(message);
            } else {
                List<String> values = new ArrayList<>();
                values.add(message);
                listOfErrors.put(fieldName, values);
            }
        });

        Map<String, Map> errors = new HashMap<>();
        errors.put("errors", listOfErrors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistRegistrationException.class)
    public ResponseEntity<?> processExistError(UserAlreadyExistRegistrationException ex, WebRequest request) {
        String result = ex.getMessage();

        Map<String, List<String>> listOfErrors = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add(result);
        listOfErrors.put("email", values);

        Map<String, Map> errors = new HashMap<>();
        errors.put("errors", listOfErrors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unathorizedError(UnauthorizedException ex, WebRequest request) {
        String result = ex.getMessage();

        Map<String, List<String>> listOfErrors = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add(result);
        listOfErrors.put("unauthorized", values);

        Map<String, Map> errors = new HashMap<>();
        errors.put("errors", listOfErrors);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundError(NotFoundException ex, WebRequest request) {
        String result = ex.getMessage();

        Map<String, List<String>> listOfErrors = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add(result);
        listOfErrors.put("not found", values);

        Map<String, Map> errors = new HashMap<>();
        errors.put("errors", listOfErrors);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    
//    /**
//     * Utility Method to generate localized message for a list of field errors
//     *
//     * @param allErrors the field errors
//     * @return the list
//     */
//    private List<String> processAllErrors(List<ObjectError> allErrors) {
//        return allErrors.stream().map(this::resolveLocalizedErrorMessage).collect(Collectors.toList());
//    }
//
//    /**
//     * Resolve localized error message. Utility method to generate a localized
//     * error message
//     *
//     * @param objectError the field error
//     * @return the string
//     */
//    private String resolveLocalizedErrorMessage(ObjectError objectError) {
//        Locale currentLocale = LocaleContextHolder.getLocale();
//        String localizedErrorMessage = messageSource.getMessage(objectError, currentLocale);
//        logger.info(localizedErrorMessage);
//        return localizedErrorMessage;
//    }
//
//    private String resolvePathFromWebRequest(WebRequest request) {
//        try {
//            return ((ServletWebRequest) request).getRequest().getAttribute("javax.servlet.forward.request_uri").toString();
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    @ExceptionHandler(value = AppException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ApiResponse handleAppException(AppException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
//    @ExceptionHandler(value = ResourceAlreadyInUseException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    @ResponseBody
//    public ApiResponse handleResourceAlreadyInUseException(ResourceAlreadyInUseException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
//    @ExceptionHandler(value = ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
////    @ExceptionHandler(value = BadRequestException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    @ResponseBody
////    public ApiResponse handleBadRequestException(BadRequestException ex, WebRequest request) {
////        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
////    }
//
//    @ExceptionHandler(value = UsernameNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ApiResponse handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
//    @ExceptionHandler(value = UserLoginException.class)
//    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
//    @ResponseBody
//    public ApiResponse handleUserLoginException(UserLoginException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
//    @ExceptionHandler(value = BadCredentialsException.class)
//    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
//    @ResponseBody
//    public ApiResponse handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
//    @ExceptionHandler(value = UserRegistrationException.class)
//    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
//    @ResponseBody
//    public ApiResponse handleUserRegistrationException(UserRegistrationException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
////    @ExceptionHandler(value = PasswordResetLinkException.class)
////    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
////    @ResponseBody
////    public ApiResponse handlePasswordResetLinkException(PasswordResetLinkException ex, WebRequest request) {
////        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
////    }
////    @ExceptionHandler(value = PasswordResetException.class)
////    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
////    @ResponseBody
////    public ApiResponse handlePasswordResetException(PasswordResetException ex, WebRequest request) {
////        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
////    }
////
////    @ExceptionHandler(value = MailSendException.class)
////    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
////    @ResponseBody
////    public ApiResponse handleMailSendException(MailSendException ex, WebRequest request) {
////        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
////    }
//    @ExceptionHandler(value = InvalidTokenRequestException.class)
//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//    @ResponseBody
//    public ApiResponse handleInvalidTokenException(InvalidTokenRequestException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
////    @ExceptionHandler(value = UpdatePasswordException.class)
////    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
////    @ResponseBody
////    public ApiResponse handleUpdatePasswordException(UpdatePasswordException ex, WebRequest request) {
////        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
////    }
//    @ExceptionHandler(value = TokenRefreshException.class)
//    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
//    @ResponseBody
//    public ApiResponse handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
//
//    @ExceptionHandler(value = UserLogoutException.class)
//    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
//    @ResponseBody
//    public ApiResponse handleUserLogoutException(UserLogoutException ex, WebRequest request) {
//        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
//    }
}
