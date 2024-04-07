package tortoisemonitor.demo.activity_log;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotNull(message = "Tortoise ID is required")
    private Long tortoiseId; // This should ideally be a mapped foreign key to the Tortoise entity

    @NotBlank(message = "Activity type is required")
    private String activityType;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime; // Nullable, no constraints needed

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes; // Nullable, no constraints needed

    // Lombok will generate constructors, getters, and setters
}
