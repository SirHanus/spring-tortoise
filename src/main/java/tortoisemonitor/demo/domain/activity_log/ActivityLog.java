package tortoisemonitor.demo.domain.activity_log;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import tortoisemonitor.demo.domain.tortoise.Tortoise;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tortoise tortoise;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Activity type is required")
    private ActivityType activityType;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;
}
