package tortoisemonitor.demo.domain.TortoiseHabitat;

import jakarta.persistence.*;
import lombok.Data;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionService;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public class TortoiseHabitatRequest {

    private String name;

    private List<UUID> tortoiseIDs = new ArrayList<>();

    private List<UUID> environmentalConditionsIDs = new ArrayList<>();

    public void toTortoiseHabitat(TortoiseHabitat tortoiseHabitat, TortoiseService tortoiseService, EnvironmentalConditionService environmentalConditionService) {
        tortoiseHabitat.setName(name);
        List<Tortoise> tortoises = this.tortoiseIDs.stream().map(tortoiseService::getTortoiseById).toList();
        tortoiseHabitat.setTortoises(tortoises);
        tortoiseHabitat.setEnvironmentalConditions(this.environmentalConditionsIDs.stream().map(environmentalConditionService::getEnvironmentalConditionById).toList());
    }
}
