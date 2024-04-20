package tortoisemonitor.demo.domain.tortoise;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TortoiseRepository extends CrudRepository<Tortoise, UUID> {
}
