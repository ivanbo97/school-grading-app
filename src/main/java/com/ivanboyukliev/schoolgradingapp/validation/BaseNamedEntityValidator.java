package com.ivanboyukliev.schoolgradingapp.validation;

import com.ivanboyukliev.schoolgradingapp.baseentity.BaseNamedEntity;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import org.springframework.stereotype.Component;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Component
public class BaseNamedEntityValidator implements EntityValidator<BaseNamedEntity> {
    @Override
    public void validate(BaseNamedEntity entity) throws EntityValidationException {
        String entityName = entity.getName();
        this.validateName(entityName);
    }

    private void validateName(String entityName) throws EntityValidationException {
        if (entityName == null) {
            throw new EntityValidationException(ERROR_ENTITY_NAME_IS_NULL);
        }
        if (entityName.isEmpty()) {
            throw new EntityValidationException(ERROR_ENTITY_NAME_IS_EMPTY);
        }
        if (entityName.isBlank()) {
            throw new EntityValidationException(ERROR_ENTITY_NAME_IS_BLANK);
        }
    }
}
