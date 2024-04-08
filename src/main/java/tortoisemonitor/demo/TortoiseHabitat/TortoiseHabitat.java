package tortoisemonitor.demo.TortoiseHabitat;

import jakarta.persistence.*;
import lombok.Data;
import tortoisemonitor.demo.tortoise.Tortoise;
import tortoisemonitor.demo.environmental_condition.EnvironmentalCondition;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class TortoiseHabitat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tortoise> tortoises;

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnvironmentalCondition> environmentalConditions;
}
