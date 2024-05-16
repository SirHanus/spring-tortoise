package tortoisemonitor.demo.domain.environmental_condition;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.tortoise.Tortoise;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EnvironmentalConditionResponse {

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

    public void fromEnvironmentalConditionResponse(EnvironmentalCondition environmentalCondition) {
        this.temperature = environmentalCondition.getTemperature();
        this.humidity = environmentalCondition.getHumidity();
        this.lightLevel = environmentalCondition.getLightLevel();
        this.timestamp = environmentalCondition.getTimestamp();
        this.tortoiseHabitatID = environmentalCondition.getHabitat().getUuid();
    }
}
