package tortoisemonitor.demo.domain.environmental_condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.utils.exceptions.NotFoundException;

import java.util.*;

@Service
public class EnvironmentalConditionService {

    private final EnvironmentalConditionRepository environmentalConditionRepository;

    @Autowired
    public EnvironmentalConditionService(EnvironmentalConditionRepository environmentalConditionRepository) {
        this.environmentalConditionRepository = environmentalConditionRepository;
    }

    public EnvironmentalCondition createEnvironmentalCondition(EnvironmentalCondition environmentalCondition) {
        return environmentalConditionRepository.save(environmentalCondition);
    }

    public List<EnvironmentalCondition> getAllEnvironmentalConditions() {
        List<EnvironmentalCondition> environmentalConditions = new ArrayList<>();
        environmentalConditionRepository.findAll().forEach(environmentalConditions::add);
        return environmentalConditions;
    }

    public EnvironmentalCondition getEnvironmentalConditionById(UUID id) {
        return environmentalConditionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public EnvironmentalCondition updateEnvironmentalCondition(UUID id, EnvironmentalCondition environmentalCondition) {
        return environmentalConditionRepository.findById(id)
                .map(existingCondition -> {
                    existingCondition.setTemperature(environmentalCondition.getTemperature());
                    existingCondition.setHumidity(environmentalCondition.getHumidity());
                    existingCondition.setLightLevel(environmentalCondition.getLightLevel());
                    existingCondition.setTimestamp(environmentalCondition.getTimestamp());
                    existingCondition.setHabitat(environmentalCondition.getHabitat());
                    return environmentalConditionRepository.save(existingCondition);
                })
                .orElseThrow(() -> new RuntimeException("Environmental condition not found with id " + id));
    }

    public void deleteEnvironmentalCondition(UUID id) {
        environmentalConditionRepository.deleteById(id);
    }

    public Map<String, Double> calculateAverageTemperaturePerHabitat(TortoiseHabitatService tortoiseHabitatService) {
        List<EnvironmentalCondition> conditions = getAllEnvironmentalConditions();
        Map<UUID, List<Double>> habitatTemperatures = new HashMap<>();

        for (EnvironmentalCondition condition : conditions) {
            UUID habitatId = condition.getHabitat().getUuid();
            habitatTemperatures.putIfAbsent(habitatId, new ArrayList<>());
            habitatTemperatures.get(habitatId).add(condition.getTemperature());
        }

        Map<String, Double> averageTemperatures = new HashMap<>();
        for (Map.Entry<UUID, List<Double>> entry : habitatTemperatures.entrySet()) {
            UUID habitatId = entry.getKey();
            List<Double> temperatures = entry.getValue();
            double sum = 0;
            for (double temp : temperatures) {
                sum += temp;
            }
            double average = sum / temperatures.size();
            TortoiseHabitat habitat = tortoiseHabitatService.getTortoiseHabitatByUuid(habitatId);
            String key = habitat.getUuid().toString() + " - " + habitat.getName();
            averageTemperatures.put(key, average);
        }

        return averageTemperatures;
    }
}
