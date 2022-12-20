package com.insider.poc1.exception;


import com.insider.poc1.dtos.response.ErrorMessageResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice// anotação para escutar as exceções;
//classe extendida para tratamento das exceções;
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_NOT_NULL = "NotNull";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";
    private static final String CONSTANT_VALIDATION_PATTERN = "Pattern";
    private static final String CONSTANT_VALIDATION_EMAIL = "Email";
    private static final String CONSTANT_VALIDATION_CNPJ = "CNPJ";
    private static final String CONSTANT_VALIDATION_CPF = "CPF";


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
       List<ErrorMessageResponse> errorMessageResponses = errorList(e.getBindingResult());
        return handleExceptionInternal(e,errorMessageResponses, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ExceptionConflict.class})
    public ResponseEntity<Object> handleExceptionConflict(ExceptionConflict e, WebRequest request){
        String messageUser = e.getMessage();
        String messageDev = e.toString();
        List<ErrorMessageResponse> errorMessageResponses = Arrays.asList(new ErrorMessageResponse(messageUser, messageDev));
        return handleExceptionInternal(e, errorMessageResponses, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e, WebRequest request){
        String messageUser = "Id not foud.";
        String messageDev = e.toString();
        List<ErrorMessageResponse> errorMessageResponses = Arrays.asList(new ErrorMessageResponse(messageUser, messageDev));
        return handleExceptionInternal(e, errorMessageResponses, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    private List<ErrorMessageResponse> errorList(BindingResult bindingResult) {
        List<ErrorMessageResponse> errorMessageResponse = new ArrayList<>();
        bindingResult.getFieldErrors()
                .forEach(fieldError -> {
                    String messageUser = handleErrorMessageForUser(fieldError);
                    String messageDev = fieldError.toString();
                    errorMessageResponse.add(new ErrorMessageResponse(messageUser, messageDev));
                });
        return errorMessageResponse;

    }

    private String handleErrorMessageForUser(FieldError fieldError) {
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {
            return fieldError.getDefaultMessage().concat(" Is Required.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_LENGTH)){
            return fieldError.getDefaultMessage().concat(String.format(" Must be between %s and %s characters.",
                    fieldError.getArguments()[2], fieldError.getArguments()[1]));
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_NULL)) {
            return fieldError.getDefaultMessage().concat(" Cannot be null.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_PATTERN)) {
            return fieldError.getDefaultMessage().concat(" Format invalid");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_EMAIL)) {
        return fieldError.getDefaultMessage();
    }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_CNPJ)) {
            return fieldError.getDefaultMessage();
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_CPF)) {
            return fieldError.getDefaultMessage();
        }
        return fieldError.toString()
;    }

}



