package tortoisemonitor.demo.domain.environmental_condition;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EnvironmentalConditionRequest {

    @NotNull(message = "Temperature is required")
    private Double temperature;

    @NotNull(message = "Humidity is required")
    private Double humidity;

    @NotNull(message = "Light level is required")
    private Double lightLevel;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @NotNull(message = "tortoiseHabitatID is required")
    private UUID tortoiseHabitatID;

    public void toEnvironmentalCondition(EnvironmentalCondition environmentalCondition, TortoiseHabitatService tortoiseHabitatService) {
        environmentalCondition.setTemperature(temperature);
        environmentalCondition.setHumidity(humidity);
        environmentalCondition.setLightLevel(lightLevel);
        environmentalCondition.setTimestamp(timestamp);
        TortoiseHabitat tortoiseHabitat = tortoiseHabitatService.getTortoiseHabitatByUuid(tortoiseHabitatID);
        environmentalCondition.setHabitat(tortoiseHabitat);
    }
}
