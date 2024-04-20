package tortoisemonitor.demo.domain.environmental_condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.utils.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}
