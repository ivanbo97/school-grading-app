package com.ivanboyukliev.schoolgradingapp.controlleradvice;

import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundCustomException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleEntityNotFoundCustomException(EntityNotFoundCustomException exception) {
        return new ErrorMessage(logError(exception));
    }

    @ExceptionHandler(EntityValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleEntityValidationException(EntityValidationException exception){
        return new ErrorMessage(logError(exception));
    }

    private String logError(Exception exception) {
        String exceptionMessage = exception.getMessage();
        log.error("Handling {} with message {}",exception.getClass().getSimpleName(),exceptionMessage);
        return exceptionMessage;
    }
}
