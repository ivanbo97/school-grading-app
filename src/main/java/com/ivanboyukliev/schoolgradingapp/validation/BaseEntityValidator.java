package com.ivanboyukliev.schoolgradingapp.validation;

import com.ivanboyukliev.schoolgradingapp.baseentity.BaseEntity;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import org.springframework.stereotype.Component;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_ENTITY_ID_IS_NULL;
import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_ENTITY_IS_NULL;

@Component
public class BaseEntityValidator implements EntityValidator<BaseEntity> {

    @Override
    public void validate(BaseEntity entity) throws EntityValidationException {
        if (entity == null) {
            throw new EntityValidationException(ERROR_ENTITY_IS_NULL);
        }
        if (entity.getId() == null) {
            throw new EntityValidationException(ERROR_ENTITY_ID_IS_NULL);
        }
    }

}