package tortoisemonitor.demo.unit_test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;
import tortoisemonitor.demo.domain.activity_log.ActivityType;
import tortoisemonitor.demo.domain.tortoise.Tortoise;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ActivityLogUnitTest {

    private final Validator validator;

    public ActivityLogUnitTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSetValidActivityLog() {
        // Given
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUuid(UUID.randomUUID());
        activityLog.setTortoise(new Tortoise());
        activityLog.setActivityType(ActivityType.FEEDING);
        activityLog.setStartTime(LocalDateTime.now());
        activityLog.setEndTime(LocalDateTime.now().plusHours(1));
        activityLog.setNotes("This is a note.");

        // When
        Set<ConstraintViolation<ActivityLog>> violations = validator.validate(activityLog);

        // Then
        assertThat(violations.size(), is(0));
    }

    @Test
    public void testSetNullActivityType() {
        // Given
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUuid(UUID.randomUUID());
        activityLog.setTortoise(new Tortoise());
        activityLog.setActivityType(null); // This should trigger a validation error
        activityLog.setStartTime(LocalDateTime.now());
        activityLog.setEndTime(LocalDateTime.now().plusHours(1));
        activityLog.setNotes("This is a note.");

        // When
        Set<ConstraintViolation<ActivityLog>> violations = validator.validate(activityLog);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<ActivityLog> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Activity type is required"));
    }

    @Test
    public void testSetNullStartTime() {
        // Given
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUuid(UUID.randomUUID());
        activityLog.setTortoise(new Tortoise());
        activityLog.setActivityType(ActivityType.FEEDING);
        activityLog.setStartTime(null); // This should trigger a validation error
        activityLog.setEndTime(LocalDateTime.now().plusHours(1));
        activityLog.setNotes("This is a note.");

        // When
        Set<ConstraintViolation<ActivityLog>> violations = validator.validate(activityLog);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<ActivityLog> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Start time is required"));
    }

    @Test
    public void testSetLongNotes() {
        // Given
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUuid(UUID.randomUUID());
        activityLog.setTortoise(new Tortoise());
        activityLog.setActivityType(ActivityType.FEEDING);
        activityLog.setStartTime(LocalDateTime.now());
        activityLog.setEndTime(LocalDateTime.now().plusHours(1));
        activityLog.setNotes("This is a very long note that exceeds the maximum length of 500 characters. ".repeat(10));

        // When
        Set<ConstraintViolation<ActivityLog>> violations = validator.validate(activityLog);

        // Then
        assertThat(violations.size(), is(1));
        ConstraintViolation<ActivityLog> violation = violations.iterator().next();
        assertThat(violation.getMessage(), is("Notes must be less than 500 characters"));
    }

    @Test
    public void testSetValidNotes() {
        // Given
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUuid(UUID.randomUUID());
        activityLog.setTortoise(new Tortoise());
        activityLog.setActivityType(ActivityType.FEEDING);
        activityLog.setStartTime(LocalDateTime.now());
        activityLog.setEndTime(LocalDateTime.now().plusHours(1));
        activityLog.setNotes("This is a valid note.");

        // When
        Set<ConstraintViolation<ActivityLog>> violations = validator.validate(activityLog);

        // Then
        assertThat(violations.size(), is(0));
    }
}
