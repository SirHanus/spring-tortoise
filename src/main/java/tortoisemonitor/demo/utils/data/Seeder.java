package tortoisemonitor.demo.utils.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatRequest;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLog;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalCondition;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionService;
import tortoisemonitor.demo.domain.tortoise.Tortoise;
import tortoisemonitor.demo.domain.tortoise.TortoiseRequest;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    private void linkTortoisesToHabitats() {
//        Map<String, Tortoise> tortoiseMap = new HashMap<>();
//        for (Tortoise tortoise : tortoiseService.getAllTortoises()) {
//            tortoiseMap.put(tortoise.getName(), tortoise);
//        }
//
//        Map<String, TortoiseHabitat> habitatMap = new HashMap<>();
//        for (TortoiseHabitat habitat : tortoiseHabitatService.getAllTortoiseHabitats()) {
//            habitatMap.put(habitat.getName(), habitat);
//        }
//
//        // Update relationships
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            InputStream inputStream = new ClassPathResource("data/tortoises.json").getInputStream();
//            TortoiseRequest[] tortoiseRequests = objectMapper.readValue(inputStream, TortoiseRequest[].class);
//            for (TortoiseRequest tortoiseRequest : tortoiseRequests) {
//                Tortoise tortoise = tortoiseMap.get(tortoiseRequest.getName());
//                if (tortoiseRequest.getHabitatID() != null) {
//                    TortoiseHabitat habitat = habitatMap.get(tortoiseRequest.getHabitatID().toString());
//                    tortoise.setHabitat(habitat);
//                    tortoiseService.updateTortoise(tortoise.getUuid(), tortoise);
//                }
//
//                for (UUID activityLogID : tortoiseRequest.getActivityLogIDs()) {
//                    ActivityLog activityLog = activityLogService.getActivityLogById(activityLogID);
//                    activityLog.setTortoise(tortoise);
//                    activityLogService.updateActivityLog(activityLog.getUuid(), activityLog);
//                }
//            }
//        } catch (IOException e) {
//            log.error("Failed to link tortoises to habitats", e);
//        }
    }
}
