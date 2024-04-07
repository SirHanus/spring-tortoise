package tortoisemonitor.demo.tortoise;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.UUID;

@Entity
@Data
public class Tortoise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Species is required")
    private String species;

    @NotNull(message = "Age is required")
    @PositiveOrZero(message = "Age cannot be negative")
    private Integer age;

    private String healthStatus; // No constraints as it may be null

    // Lombok will generate constructors, getters, and setters
}

