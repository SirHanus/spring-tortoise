package tortoisemonitor.demo.domain.environmental_condition;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;

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
    @PositiveOrZero(message = "Humidity cannot be negative")
    private Double humidity;

    @NotNull(message = "Light level is required")
    @PositiveOrZero(message = "Light level cannot be negative")
    private Double lightLevel;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitat_uuid")
    private TortoiseHabitat habitat;
}
