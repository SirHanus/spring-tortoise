package tortoisemonitor.demo.unit_test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseSpecies;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TortoiseHabitatUnitTest {

    private final Validator validator;

    public TortoiseHabitatUnitTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAddTortoise() {
        // Given
        TortoiseHabitat habitat = new TortoiseHabitat();
        habitat.setUuid(UUID.randomUUID());
        habitat.setName("Habitat1");

        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");

        // When
        habitat.addTortoise(tortoise);

        // Then
        assertThat(habitat.getTortoises().size(), is(1));
        assertThat(habitat.getTortoises().get(0), is(tortoise));
    }

    @Test
    public void testAddEnvironmentalCondition() {
        // Given
        TortoiseHabitat habitat = new TortoiseHabitat();
        habitat.setUuid(UUID.randomUUID());
        habitat.setName("Habitat1");

        EnvironmentalCondition condition = new EnvironmentalCondition();
        condition.setTemperature(25.0);
        condition.setHumidity(60.0);
        condition.setLightLevel(500.0);

        // When
        habitat.addEnvironmentalCondition(condition);

        // Then
        assertThat(habitat.getEnvironmentalConditions().size(), is(1));
        assertThat(habitat.getEnvironmentalConditions().get(0), is(condition));
    }

    @Test
    public void testSetNullName() {
        // Given
        TortoiseHabitat habitat = new TortoiseHabitat();
        habitat.setUuid(UUID.randomUUID());
        habitat.setName(null); // This should trigger a validation error

        // When & Then
        Set<ConstraintViolation<TortoiseHabitat>> violations = validator.validate(habitat);
        assertThat(violations.size(), is(1));
        ConstraintViolation<TortoiseHabitat> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("must not be empty"));
    }

    @Test
    public void testSetEmptyName() {
        // Given
        TortoiseHabitat habitat = new TortoiseHabitat();
        habitat.setUuid(UUID.randomUUID());
        habitat.setName(""); // This should trigger a validation error

        // When & Then
        Set<ConstraintViolation<TortoiseHabitat>> violations = validator.validate(habitat);
        assertThat(violations.size(), is(1));
        ConstraintViolation<TortoiseHabitat> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("must not be empty"));
    }

    @Test
    public void testSetEmptyTortoises() {
        // Given
        TortoiseHabitat habitat = new TortoiseHabitat();
        habitat.setUuid(UUID.randomUUID());
        habitat.setName("Habitat1");
        habitat.setTortoises(null); // This should trigger a validation error

        // When & Then
        Set<ConstraintViolation<TortoiseHabitat>> violations = validator.validate(habitat);
        assertThat(violations.size(), is(1));
        ConstraintViolation<TortoiseHabitat> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("must not be null"));
    }

    @Test
    public void testSetEmptyEnvironmentalConditions() {
        // Given
        TortoiseHabitat habitat = new TortoiseHabitat();
        habitat.setUuid(UUID.randomUUID());
        habitat.setName("Habitat1");
        habitat.setEnvironmentalConditions(null); // This should trigger a validation error

        // When & Then
        Set<ConstraintViolation<TortoiseHabitat>> violations = validator.validate(habitat);
        assertThat(violations.size(), is(1));
        ConstraintViolation<TortoiseHabitat> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("must not be null"));
    }
}
