package tortoisemonitor.demo.utils.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatRequest;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;
import tortoisemonitor.demo.domain.activity_log.ActivityLogRequest;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionRequest;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionService;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseRequest;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

        log.info("Seeding data from JSON files...");
        importDataFromJson();
    }

    private void importDataFromJson() {
        importTortoiseHabitats("tortoiseHabitats.json");
        importTortoises("tortoises.json");
        linkTortoisesToHabitats();
        importEnvironmentalConditions("environmentalConditions.json");
        importActivityLogs("activityLogs.json");
        linkEnvironmentalConditions();
        linkActivityLogsToTortoises();
    }

    private void importTortoises(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource(path).getInputStream();
            TortoiseRequest[] tortoiseRequests = objectMapper.readValue(inputStream, TortoiseRequest[].class);
            for (TortoiseRequest tortoiseRequest : tortoiseRequests) {
                Tortoise tortoise = new Tortoise();
                tortoise.setName(tortoiseRequest.getName());
                tortoise.setSpecies(tortoiseRequest.getSpecies());
                tortoise.setAge(tortoiseRequest.getAge());
                tortoise.setHealthStatus(tortoiseRequest.getHealthStatus());
                tortoiseService.createTortoise(tortoise);
            }
        } catch (IOException e) {
            log.error("Failed to import tortoises from JSON file", e);
        }
    }

    private void importTortoiseHabitats(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource(path).getInputStream();
            TortoiseHabitatRequest[] habitatRequests = objectMapper.readValue(inputStream, TortoiseHabitatRequest[].class);
            for (TortoiseHabitatRequest habitatRequest : habitatRequests) {
                TortoiseHabitat habitat = new TortoiseHabitat();
                habitat.setName(habitatRequest.getName());
                tortoiseHabitatService.createTortoiseHabitat(habitat);
            }
        } catch (IOException e) {
            log.error("Failed to import tortoise habitats from JSON file", e);
        }
    }

    private void importEnvironmentalConditions(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            InputStream inputStream = new ClassPathResource(path).getInputStream();
            EnvironmentalConditionRequest[] conditionRequests = objectMapper.readValue(inputStream, EnvironmentalConditionRequest[].class);
            for (EnvironmentalConditionRequest conditionRequest : conditionRequests) {
                EnvironmentalCondition condition = new EnvironmentalCondition();
                conditionRequest.toEnvironmentalCondition(condition, tortoiseHabitatService);
                environmentalConditionService.createEnvironmentalCondition(condition);
            }
        } catch (IOException e) {
            log.error("Failed to import environmental conditions from JSON file", e);
        }
    }

    private void importActivityLogs(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            InputStream inputStream = new ClassPathResource(path).getInputStream();
            ActivityLogRequest[] activityLogRequests = objectMapper.readValue(inputStream, ActivityLogRequest[].class);
            for (ActivityLogRequest activityLogRequest : activityLogRequests) {
                ActivityLog activityLog = new ActivityLog();
                activityLogRequest.toActivityLog(activityLog, tortoiseService);
                activityLogService.createActivityLog(activityLog);
            }
        } catch (IOException e) {
            log.error("Failed to import activity logs from JSON file", e);
        }
    }

    private void linkTortoisesToHabitats() {

        List<TortoiseHabitat> tortoiseHabitats = tortoiseHabitatService.getAllTortoiseHabitats();
        List<Tortoise> tortoises = tortoiseService.getAllTortoises();

        Random random = new Random();

        for (Tortoise tortoise : tortoises) {
            TortoiseHabitat randomHabitat = tortoiseHabitats.get(random.nextInt(tortoiseHabitats.size()));
            tortoise.setHabitat(randomHabitat);
            tortoiseService.updateTortoise(tortoise.getUuid(), tortoise);
        }
    }

    private void linkEnvironmentalConditions() {
        List<EnvironmentalCondition> environmentalConditions = environmentalConditionService.getAllEnvironmentalConditions();
        List<TortoiseHabitat> tortoiseHabitats = tortoiseHabitatService.getAllTortoiseHabitats();

        Random random = new Random();
        for (EnvironmentalCondition environmentalCondition : environmentalConditions) {
            TortoiseHabitat randomHabitat = tortoiseHabitats.get(random.nextInt(tortoiseHabitats.size()));
            environmentalCondition.setHabitat(randomHabitat);
            environmentalConditionService.updateEnvironmentalCondition(environmentalCondition.getUuid(), environmentalCondition);

        }
    }

    private void linkActivityLogsToTortoises() {
        List<ActivityLog> activityLogs = activityLogService.getAllActivityLogs();

        activityLogs.sort((log1, log2) -> log2.getStartTime().compareTo(log1.getStartTime()));

        List<Tortoise> tortoises = tortoiseService.getAllTortoises();

        if (tortoises.size() < 3) {
            log.warn("Not enough tortoises to link activity logs to.");
            return;
        }

        Tortoise tortoise1 = tortoises.get(0);
        Tortoise tortoise2 = tortoises.get(1);
        Tortoise tortoise3 = tortoises.get(2);

        for (int i = 0; i < activityLogs.size(); i++) {
            ActivityLog activityLog = activityLogs.get(i);
            if (i % 3 == 0) {
                activityLog.setTortoise(tortoise1);
            } else if (i % 3 == 1) {
                activityLog.setTortoise(tortoise2);
            } else {
                activityLog.setTortoise(tortoise3);
            }
            activityLogService.updateActivityLog(activityLog.getUuid(), activityLog);
        }
    }

}
