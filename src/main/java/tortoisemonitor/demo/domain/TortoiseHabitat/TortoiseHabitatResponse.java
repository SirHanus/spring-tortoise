package tortoisemonitor.demo.domain.TortoiseHabitat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;
import tortoisemonitor.demo.domain.tortoise.Tortoise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public class TortoiseHabitatResponse {

    private String name;

    private List<UUID> tortoiseIDs = new ArrayList<>();

    private List<UUID> environmentalConditionsIDs = new ArrayList<>();

    public void fromTortoiseHabitat(TortoiseHabitat tortoiseHabitat) {
        this.name = tortoiseHabitat.getName();
        this.tortoiseIDs = tortoiseHabitat.getTortoises().stream().map(Tortoise::getUuid).toList();
        this.environmentalConditionsIDs = tortoiseHabitat.getEnvironmentalConditions().stream().map(EnvironmentalCondition::getUuid).toList();
    }
}
