package tortoisemonitor.demo.domain.tortoise;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;
import tortoisemonitor.demo.utils.exceptions.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class TortoiseRequest {


    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Species is required")
    private TortoiseSpecies species;

    @NotNull(message = "Age is required")
    private Integer age;

    private String healthStatus;

    @NotEmpty
    private String habitatName;

    public void toTortoise(Tortoise tortoise, ActivityLogService activityLogService, TortoiseHabitatService tortoiseHabitatService) {
        tortoise.setName(name);
        tortoise.setSpecies(species);
        tortoise.setAge(age);
        tortoise.setHealthStatus(healthStatus);

        if (!habitatName.isEmpty()) {
            Optional<TortoiseHabitat> maybeTortoiseHabitat = tortoiseHabitatService.getAllTortoiseHabitats().stream().filter(x -> x.getName().equals(habitatName)).findFirst();
            if (maybeTortoiseHabitat.isPresent()) {
                tortoise.setHabitat(maybeTortoiseHabitat.get());
            } else {
                throw new InvalidDataException();
            }

        }
    }
}
