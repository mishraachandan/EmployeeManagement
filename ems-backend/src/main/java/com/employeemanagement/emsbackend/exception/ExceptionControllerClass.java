package com.employeemanagement.emsbackend.exception;


import com.employeemanagement.emsbackend.dto.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerClass {


    private final Environment environment;

    @Autowired
    public ExceptionControllerClass(Environment environment){
        this.environment = environment;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo>  exceptionHandler(){
        ErrorInfo errorInfo = new ErrorInfo(environment.getProperty("GENERAL.EXCEPTION"),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> employeeExceptionHandler(ResourceNotFoundException resourceNotFoundException){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(environment.getProperty(resourceNotFoundException.getMessage()));
        errorInfo.setTimestamp(LocalDateTime.now());
        errorInfo.setErrorCode(HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

}
