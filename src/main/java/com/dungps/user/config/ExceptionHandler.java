package com.dungps.user.config;

import com.dungps.user.common.exception.UserException;
import com.dungps.user.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
    public ResponseEntity handleUserException(UserException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(ex.getStatusCode()).body(new ResponseDto(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity handleUserException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(ex.getMessage()));
    }
}
