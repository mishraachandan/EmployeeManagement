package com.employeemanagement.emsbackend.exception;


import com.employeemanagement.emsbackend.dto.ErrorInfo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> validationExceptionHandler(MethodArgumentNotValidException exception){
        ErrorInfo errorInfo = new ErrorInfo();
//        String test = exception.getBindingResult().toString();
//        List<ObjectError> allErr = exception.getBindingResult().getAllErrors();
        String errorMsg = exception.getBindingResult()
                .getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining("."));
        errorInfo.setErrorMessage(errorMsg);
        errorInfo.setTimestamp(LocalDateTime.now());
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> constraintValidationExceptionHandler(ConstraintViolationException exception){
        ErrorInfo errorInfo = new ErrorInfo();
//        Set<ConstraintViolation<?>> con = exception.getConstraintViolations();
        String errorMsg = exception.getConstraintViolations().stream().map(x -> x.getMessage()).collect(Collectors.joining("."));
        errorInfo.setErrorMessage(errorMsg);
        errorInfo.setTimestamp(LocalDateTime.now());
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorInfo> emailValidationExceptionHandler(EmailAlreadyExistException exception){
        ErrorInfo errorInfo = new ErrorInfo();
//        Set<ConstraintViolation<?>> con = exception.getConstraintViolations();
//        String errorMsg = exception.getConstraintViolations().stream().map(x -> x.getMessage()).collect(Collectors.joining("."));
        errorInfo.setErrorMessage(environment.getProperty(exception.getMessage()));
        errorInfo.setTimestamp(LocalDateTime.now());
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
