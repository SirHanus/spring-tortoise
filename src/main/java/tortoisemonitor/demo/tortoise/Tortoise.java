package tortoisemonitor.demo.tortoise;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import tortoisemonitor.demo.activity_log.ActivityLog;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Tortoise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Species is required")
    private String species;

    @NotNull(message = "Age is required")
    @PositiveOrZero(message = "Age cannot be negative")
    private Integer age;

    private String healthStatus;

    @OneToMany(mappedBy = "tortoise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityLog> activityLogs;
}
