package tortoisemonitor.demo.domain.tortoise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public class TortoiseResponse {

    private UUID uuid;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Species is required")
    private TortoiseSpecies species;

    @NotNull(message = "Age is required")
    private Integer age;

    private String healthStatus;

    private List<UUID> activityLogIDs = new ArrayList<>();

    private String habitatName;

    public void fromTortoise(Tortoise tortoise) {
        this.uuid = tortoise.getUuid();
        this.name = tortoise.getName();
        this.species = tortoise.getSpecies();
        this.age = tortoise.getAge();
        this.healthStatus = tortoise.getHealthStatus();
        this.activityLogIDs = tortoise.getActivityLogs().stream().map(ActivityLog::getUuid).toList();
        this.habitatName = tortoise.getHabitat().getName();
    }
}
