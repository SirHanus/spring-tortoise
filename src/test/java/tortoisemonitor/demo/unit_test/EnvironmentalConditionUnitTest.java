package tortoisemonitor.demo.unit_test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EnvironmentalConditionUnitTest {

    private final Validator validator;

    public EnvironmentalConditionUnitTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSetValidEnvironmentalCondition() {
        // Given
        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setUuid(UUID.randomUUID());
        condition.setTemperature(25.0);
        condition.setHumidity(60.0);
        condition.setLightLevel(500.0);
        condition.setTimestamp(LocalDateTime.now());
        condition.setHabitat(new TortoiseHabitat());

        // When
        Set<ConstraintViolation<EnvironmentalCondition>> violations = validator.validate(condition);

        // Then
        assertThat(violations.size(), is(0));
    }

    @Test
    public void testSetNullTemperature() {
        // Given
        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setUuid(UUID.randomUUID());
        condition.setTemperature(null); // This should trigger a validation error
        condition.setHumidity(60.0);
        condition.setLightLevel(500.0);
        condition.setTimestamp(LocalDateTime.now());
        condition.setHabitat(new TortoiseHabitat());

        // When
        Set<ConstraintViolation<EnvironmentalCondition>> violations = validator.validate(condition);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<EnvironmentalCondition> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Temperature is required"));
    }

    @Test
    public void testSetNullHumidity() {
        // Given
        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setUuid(UUID.randomUUID());
        condition.setTemperature(25.0);
        condition.setHumidity(null); // This should trigger a validation error
        condition.setLightLevel(500.0);
        condition.setTimestamp(LocalDateTime.now());
        condition.setHabitat(new TortoiseHabitat());

        // When
        Set<ConstraintViolation<EnvironmentalCondition>> violations = validator.validate(condition);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<EnvironmentalCondition> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Humidity is required"));
    }

    @Test
    public void testSetNegativeHumidity() {
        // Given
        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setUuid(UUID.randomUUID());
        condition.setTemperature(25.0);
        condition.setHumidity(-10.0); // This should trigger a validation error
        condition.setLightLevel(500.0);
        condition.setTimestamp(LocalDateTime.now());
        condition.setHabitat(new TortoiseHabitat());

        // When
        Set<ConstraintViolation<EnvironmentalCondition>> violations = validator.validate(condition);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<EnvironmentalCondition> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Humidity cannot be negative"));
    }

    @Test
    public void testSetNullLightLevel() {
        // Given
        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setUuid(UUID.randomUUID());
        condition.setTemperature(25.0);
        condition.setHumidity(60.0);
        condition.setLightLevel(null); // This should trigger a validation error
        condition.setTimestamp(LocalDateTime.now());
        condition.setHabitat(new TortoiseHabitat());

        // When
        Set<ConstraintViolation<EnvironmentalCondition>> violations = validator.validate(condition);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<EnvironmentalCondition> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Light level is required"));
    }

    @Test
    public void testSetNegativeLightLevel() {
        // Given
        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setUuid(UUID.randomUUID());
        condition.setTemperature(25.0);
        condition.setHumidity(60.0);
        condition.setLightLevel(-100.0); // This should trigger a validation error
        condition.setTimestamp(LocalDateTime.now());
        condition.setHabitat(new TortoiseHabitat());

        // When
        Set<ConstraintViolation<EnvironmentalCondition>> violations = validator.validate(condition);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<EnvironmentalCondition> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Light level cannot be negative"));
    }

    @Test
    public void testSetNullTimestamp() {
        // Given
        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setUuid(UUID.randomUUID());
        condition.setTemperature(25.0);
        condition.setHumidity(60.0);
        condition.setLightLevel(500.0);
        condition.setTimestamp(null); // This should trigger a validation error
        condition.setHabitat(new TortoiseHabitat());

        // When
        Set<ConstraintViolation<EnvironmentalCondition>> violations = validator.validate(condition);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<EnvironmentalCondition> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Timestamp is required"));
    }
}
