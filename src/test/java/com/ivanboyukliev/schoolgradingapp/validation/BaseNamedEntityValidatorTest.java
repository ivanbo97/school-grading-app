package com.ivanboyukliev.schoolgradingapp.validation;

import com.ivanboyukliev.schoolgradingapp.baseentity.BaseNamedEntity;
import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;

@ExtendWith(MockitoExtension.class)
class BaseNamedEntityValidatorTest {

    private BaseNamedEntityValidator entityValidator = new BaseNamedEntityValidator();

    @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
    @MethodSource("supplyEntities")
    void validate(BaseNamedEntity entity, String expectedErrorMessage) {
        Exception exception = Assertions.assertThrows(EntityValidationException.class,
                () -> entityValidator.validate(entity));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }

    private static Stream<Arguments> supplyEntities() {
        return Stream.of(
                Arguments.of(new Course(1L, null), ERROR_ENTITY_NAME_IS_NULL),
                Arguments.of(new Course(2L, ""), ERROR_ENTITY_NAME_IS_EMPTY),
                Arguments.of(new Course(3L, " "), ERROR_ENTITY_NAME_IS_BLANK)
        );
    }
}