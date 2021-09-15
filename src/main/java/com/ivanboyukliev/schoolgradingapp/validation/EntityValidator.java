package com.ivanboyukliev.schoolgradingapp.validation;

import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;

public interface EntityValidator<T> {

    void validate(T entity) throws EntityValidationException;
}
