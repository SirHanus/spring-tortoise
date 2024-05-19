package tortoisemonitor.demo.domain.tortoise;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;

import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Species is required")
    private TortoiseSpecies species;

    @NotNull(message = "Age is required")
    @PositiveOrZero(message = "Age cannot be negative")
    private Integer age;

    private String healthStatus;

    @OneToMany(mappedBy = "tortoise", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityLog> activityLogs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitat_uuid")
    private TortoiseHabitat habitat;

    public void addActivityLog(ActivityLog activityLog) {
        activityLogs.add(activityLog);
    }
}
