package tortoisemonitor.demo.domain.activity_log;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ActivityLogRepository extends CrudRepository<ActivityLog, UUID> {
}
