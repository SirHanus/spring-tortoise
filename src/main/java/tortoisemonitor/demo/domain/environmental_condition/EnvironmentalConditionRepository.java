package tortoisemonitor.demo.domain.environmental_condition;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EnvironmentalConditionRepository extends CrudRepository<EnvironmentalCondition, UUID> {
}
