package com.pos.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.pos.model.MessageData;
import com.pos.service.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageData handle(ApiException apiException) {
        MessageData data = new MessageData();
        data.setMessage(apiException.getMessage());
        return data;
    }

    @ExceptionHandler(value = NumberFormatException.class)// TODO: handle
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageData handleHttpMessageNotReadableException(NumberFormatException ex) {
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        MessageData data = new MessageData();
        data.setMessage("invalid input -- can't convert");
        return data;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageData handle(Throwable e) {
        MessageData data = new MessageData();
        data.setMessage("An unknown error has occurred - " + e.getMessage());
        return data;
    }
}