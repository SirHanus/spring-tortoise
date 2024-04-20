package tortoisemonitor.demo.domain.activity_log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.utils.exceptions.NotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    @Autowired
    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public ActivityLog createActivityLog(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    public List<ActivityLog> getAllActivityLogs() {
        List<ActivityLog> activityLogs = new ArrayList<>();
        activityLogRepository.findAll().forEach(activityLogs::add);
        return activityLogs;
    }

    public ActivityLog getActivityLogById(UUID id) {
        return activityLogRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public ActivityLog updateActivityLog(UUID id, ActivityLog activityLog) {
        return activityLogRepository.findById(id)
                .map(existingActivityLog -> {
                    // Copy properties from the new activity log to the existing one
                    existingActivityLog.setActivityType(activityLog.getActivityType());
                    existingActivityLog.setNotes(activityLog.getNotes());
                    existingActivityLog.setStartTime(activityLog.getStartTime());
                    existingActivityLog.setEndTime(activityLog.getEndTime());
                    existingActivityLog.setTortoise(activityLog.getTortoise());

                    return activityLogRepository.save(existingActivityLog);
                })
                .orElseThrow(NotFoundException::new);
    }

    public void deleteActivityLog(UUID id) {
        activityLogRepository.deleteById(id);
    }
}
