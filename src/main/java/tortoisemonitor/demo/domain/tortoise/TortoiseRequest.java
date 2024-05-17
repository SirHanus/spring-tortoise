package tortoisemonitor.demo.domain.tortoise;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
public class TortoiseRequest {


    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Species is required")
    private TortoiseSpecies species;

    @NotNull(message = "Age is required")
    private Integer age;

    private String healthStatus;

    private List<UUID> activityLogIDs = new ArrayList<>();

    private UUID habitatID;

    public void toTortoise(Tortoise tortoise, ActivityLogService activityLogService, TortoiseHabitatService tortoiseHabitatService) {
        tortoise.setName(name);
        tortoise.setSpecies(species);
        tortoise.setAge(age);
        tortoise.setHealthStatus(healthStatus);
        tortoise.setActivityLogs(this.activityLogIDs.stream().map(activityLogService::getActivityLogById).toList());
        tortoise.setHabitat(tortoiseHabitatService.getTortoiseHabitatByUuid(habitatID));
    }
}
