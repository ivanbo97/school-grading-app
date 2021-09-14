package com.ivanboyukliev.schoolgradingapp.exception;

public class EntityNotFoundCustomException extends RuntimeException{
    public EntityNotFoundCustomException() {
        super();
    }

    public EntityNotFoundCustomException(String message) {
        super(message);
    }

    public EntityNotFoundCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundCustomException(Throwable cause) {
        super(cause);
    }

}
