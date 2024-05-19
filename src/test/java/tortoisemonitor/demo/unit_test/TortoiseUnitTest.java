package tortoisemonitor.demo.unit_test;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.activity_log.ActivityType;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseSpecies;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TortoiseUnitTest {
    private final Validator validator;

    public TortoiseUnitTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSetHabitat() {
        // Given
        TortoiseHabitat habitat1 = new TortoiseHabitat();
        habitat1.setName("Habitat1Test");
        TortoiseHabitat habitat2 = new TortoiseHabitat();
        habitat2.setName("Habitat2Test");

        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(habitat1);

        // When
        tortoise.setHabitat(habitat2);

        // Then
        assertThat(tortoise.getHabitat(), is(habitat2));
    }

    @Test
    public void testSetHealthStatus() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        // When
        tortoise.setHealthStatus("Sick");

        // Then
        assertThat(tortoise.getHealthStatus(), is("Sick"));
    }

    @Test
    public void testSetAge() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        // When
        tortoise.setAge(6);

        // Then
        assertThat(tortoise.getAge(), is(6));
    }

    @Test
    public void testSetHabitatToSameValue() {
        // Given
        TortoiseHabitat habitat = new TortoiseHabitat();
        habitat.setName("HabitatTest");

        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(habitat);

        // When
        tortoise.setHabitat(habitat);

        // Then
        assertThat(tortoise.getHabitat(), is(habitat));
    }

    @Test
    public void testSetInvalidAge() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(-5); // This should trigger a validation error
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        // When & Then
        Set<ConstraintViolation<Tortoise>> violations = validator.validate(tortoise);
        assertThat(violations.size(), is(1));
        ConstraintViolation<Tortoise> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Age cannot be negative"));
    }

    @Test
    public void testSetNullName() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName(null); // This should trigger a validation error
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        // When & Then
        Set<ConstraintViolation<Tortoise>> violations = validator.validate(tortoise);
        assertThat(violations.size(), is(1));
        ConstraintViolation<Tortoise> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Name is required"));
    }

    @Test
    public void testSetEmptyName() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName(""); // This should trigger a validation error
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        // When & Then
        Set<ConstraintViolation<Tortoise>> violations = validator.validate(tortoise);
        assertThat(violations.size(), is(1));
        ConstraintViolation<Tortoise> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Name is required"));
    }

    @Test
    public void testSetNullSpecies() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(null); // This should trigger a validation error
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        // When & Then
        Set<ConstraintViolation<Tortoise>> violations = validator.validate(tortoise);
        assertThat(violations.size(), is(1));
        ConstraintViolation<Tortoise> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Species is required"));
    }

    @Test
    public void testSetNullAge() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(null); // This should trigger a validation error
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        // When & Then
        Set<ConstraintViolation<Tortoise>> violations = validator.validate(tortoise);
        assertThat(violations.size(), is(1));
        ConstraintViolation<Tortoise> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Age is required"));
    }

    @Test
    public void testAddActivityLog() {
        // Given
        Tortoise tortoise = new Tortoise();
        tortoise.setUuid(UUID.randomUUID());
        tortoise.setName("Tortoise1");
        tortoise.setSpecies(TortoiseSpecies.HERMANN);
        tortoise.setAge(5);
        tortoise.setHealthStatus("Healthy");
        tortoise.setHabitat(new TortoiseHabitat());

        ActivityLog log = new ActivityLog();
        log.setActivityType(ActivityType.FEEDING);
        log.setStartTime(LocalDateTime.now());
        log.setEndTime(LocalDateTime.now().plusHours(1));

        // When
        tortoise.addActivityLog(log);

        // Then
        assertThat(tortoise.getActivityLogs().size(), is(1));
        assertThat(tortoise.getActivityLogs().get(0), is(log));
    }
}
