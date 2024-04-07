package tortoisemonitor.demo.environmental_condition;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class EnvironmentalCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotNull(message = "Temperature is required")
    private Double temperature;

    @NotNull(message = "Humidity is required")
    private Double humidity;

    @NotNull(message = "Light level is required")
    private Double lightLevel;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
}
