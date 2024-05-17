package tortoisemonitor.demo.domain.activity_log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;
import tortoisemonitor.demo.utils.exceptions.NotFoundException;

import java.time.DayOfWeek;

import java.util.*;

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

    public Map<String, Long> calculateTotalActivityDurationPerTortoise(TortoiseService tortoiseService) {
        List<ActivityLog> activityLogs = getAllActivityLogs();
        Map<UUID, Long> totalDurations = new HashMap<>();

        for (ActivityLog log : activityLogs) {
            UUID tortoiseId = log.getTortoise().getUuid();
            long duration = java.time.Duration.between(log.getStartTime(), log.getEndTime()).toMinutes();
            totalDurations.put(tortoiseId, totalDurations.getOrDefault(tortoiseId, 0L) + duration);
        }

        Map<String, Long> totalDurationsWithNames = new HashMap<>();
        for (Map.Entry<UUID, Long> entry : totalDurations.entrySet()) {
            UUID tortoiseId = entry.getKey();
            Tortoise tortoise = tortoiseService.getTortoiseById(tortoiseId);
            String key = tortoise.getUuid().toString() + " - " + tortoise.getName();
            totalDurationsWithNames.put(key, entry.getValue());
        }

        return totalDurationsWithNames;
    }

    public Map<String, ActivityType> calculateMostCommonActivityTypePerTortoise(TortoiseService tortoiseService) {
        List<ActivityLog> activityLogs = getAllActivityLogs();
        Map<UUID, Map<ActivityType, Integer>> activityCounts = new HashMap<>();

        for (ActivityLog log : activityLogs) {
            UUID tortoiseId = log.getTortoise().getUuid();
            activityCounts.putIfAbsent(tortoiseId, new HashMap<>());
            Map<ActivityType, Integer> counts = activityCounts.get(tortoiseId);
            counts.put(log.getActivityType(), counts.getOrDefault(log.getActivityType(), 0) + 1);
        }

        Map<String, ActivityType> mostCommonActivities = new HashMap<>();
        for (Map.Entry<UUID, Map<ActivityType, Integer>> entry : activityCounts.entrySet()) {
            UUID tortoiseId = entry.getKey();
            Map<ActivityType, Integer> counts = entry.getValue();
            ActivityType mostCommon = counts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            Tortoise tortoise = tortoiseService.getTortoiseById(tortoiseId);
            String key = tortoise.getUuid().toString() + " - " + tortoise.getName();
            mostCommonActivities.put(key, mostCommon);
        }

        return mostCommonActivities;
    }

    public Map<DayOfWeek, Long> calculateActivityDistributionPerDay() {
        List<ActivityLog> activityLogs = getAllActivityLogs();
        Map<DayOfWeek, Long> activityDistribution = new HashMap<>();

        for (ActivityLog log : activityLogs) {
            DayOfWeek dayOfWeek = log.getStartTime().getDayOfWeek();
            activityDistribution.put(dayOfWeek, activityDistribution.getOrDefault(dayOfWeek, 0L) + 1);
        }

        return activityDistribution;
    }
}
