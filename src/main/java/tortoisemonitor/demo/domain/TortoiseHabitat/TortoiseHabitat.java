package tortoisemonitor.demo.domain.TortoiseHabitat;

import jakarta.persistence.*;
import lombok.Data;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;

import java.util.ArrayList;
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
    private List<Tortoise> tortoises = new ArrayList<>();

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnvironmentalCondition> environmentalConditions = new ArrayList<>();


    public void addTortoise(Tortoise tortoise) {
        tortoises.add(tortoise);
    }

    public void addEnvironmentalCondition(EnvironmentalCondition environmentalCondition) {
        environmentalConditions.add(environmentalCondition);
    }
}
