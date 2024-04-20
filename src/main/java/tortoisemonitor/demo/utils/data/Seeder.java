package tortoisemonitor.demo.utils.data;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;
import tortoisemonitor.demo.domain.activity_log.ActivityType;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionService;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;
import tortoisemonitor.demo.domain.tortoise.TortoiseSpecies;

import java.time.LocalDateTime;

@Service
@Slf4j
public class Seeder {
    private final TortoiseHabitatService tortoiseHabitatService;
    private final TortoiseService tortoiseService;
    private final EnvironmentalConditionService environmentalConditionService;
    private final ActivityLogService activityLogService;

    public Seeder(TortoiseHabitatService tortoiseHabitatService, TortoiseService tortoiseService, EnvironmentalConditionService environmentalConditionService, ActivityLogService activityLogService) {
        this.tortoiseHabitatService = tortoiseHabitatService;
        this.tortoiseService = tortoiseService;
        this.environmentalConditionService = environmentalConditionService;
        this.activityLogService = activityLogService;
    }

    private boolean shouldSeed() {
        return tortoiseService.getAllTortoises().isEmpty();
    }

    @PostConstruct
    public void seed() {
        if (!shouldSeed()) {
            log.info("Data already seeded...");
            return;
        }
        TortoiseHabitat tortoiseHabitat = new TortoiseHabitat();
        tortoiseHabitat.setName("Terarium1");

        tortoiseHabitatService.createTortoiseHabitat(tortoiseHabitat);

        EnvironmentalCondition environmentalCondition = new EnvironmentalCondition();
        environmentalCondition.setHabitat(tortoiseHabitat);
        environmentalCondition.setTimestamp(LocalDateTime.now());
        environmentalCondition.setTemperature(30.2);
        environmentalCondition.setLightLevel(10.1);
        environmentalCondition.setHumidity(10.2);

        environmentalConditionService.createEnvironmentalCondition(environmentalCondition);

        Tortoise tortoise1 = new Tortoise();
        tortoise1.setHabitat(tortoiseHabitat);
        tortoise1.setSpecies(TortoiseSpecies.HERMANN);
        tortoise1.setName("Skvrnin");
        tortoise1.setAge(2);
        tortoise1.setHealthStatus("Perfectly fine");
        tortoiseService.createTortoise(tortoise1);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setActivityType(ActivityType.BASKING);
        activityLog.setNotes("asd");
        activityLog.setStartTime(LocalDateTime.now());
        activityLog.setEndTime(LocalDateTime.now());
        activityLogService.createActivityLog(activityLog);


        activityLog.setTortoise(tortoise1);
        tortoise1.addActivityLog(activityLog);
        tortoiseService.updateTortoise(tortoise1.getUuid(), tortoise1);
        activityLogService.updateActivityLog(activityLog.getUuid(), activityLog);

        tortoiseHabitat.addTortoise(tortoise1);
        tortoiseHabitat.addEnvironmentalCondition(environmentalCondition);

        tortoiseHabitatService.updateTortoiseHabitat(tortoiseHabitat.getUuid(), tortoiseHabitat);
    }

}
