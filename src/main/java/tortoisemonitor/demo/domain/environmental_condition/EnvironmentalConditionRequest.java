package tortoisemonitor.demo.domain.environmental_condition;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentalConditionRequest {

    @NotNull(message = "Temperature is required")
    private Double temperature;

    @NotNull(message = "Humidity is required")
    @PositiveOrZero(message = "Humidity cannot be negative")
    private Double humidity;

    @NotNull(message = "Light level is required")
    @PositiveOrZero(message = "Light level cannot be negative")
    private Double lightLevel;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    private UUID tortoiseHabitatID;

    public void toEnvironmentalCondition(EnvironmentalCondition environmentalCondition, TortoiseHabitatService tortoiseHabitatService) {
        environmentalCondition.setTemperature(temperature);
        environmentalCondition.setHumidity(humidity);
        environmentalCondition.setLightLevel(lightLevel);
        environmentalCondition.setTimestamp(timestamp);
        if (tortoiseHabitatID != null) {
            TortoiseHabitat tortoiseHabitat = tortoiseHabitatService.getTortoiseHabitatByUuid(tortoiseHabitatID);
            environmentalCondition.setHabitat(tortoiseHabitat);
        } else if (!tortoiseHabitatService.getAllTortoiseHabitats().isEmpty()) {
            environmentalCondition.setHabitat(tortoiseHabitatService.getAllTortoiseHabitats().getLast());
        }
    }
}
