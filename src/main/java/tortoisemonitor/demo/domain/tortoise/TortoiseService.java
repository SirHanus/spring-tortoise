package tortoisemonitor.demo.domain.tortoise;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitat;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.utils.exceptions.NotFoundException;

import java.util.*;

@Service
public class TortoiseService {

    private final TortoiseRepository tortoiseRepository;


    @Autowired
    public TortoiseService(TortoiseRepository tortoiseRepository) {
        this.tortoiseRepository = tortoiseRepository;
    }

    public Tortoise createTortoise(Tortoise tortoise) {
        return tortoiseRepository.save(tortoise);
    }

    public List<Tortoise> getAllTortoises() {
        List<Tortoise> tortoises = new ArrayList<>();
        tortoiseRepository.findAll().forEach(tortoises::add);
        return tortoises;
    }

    public Tortoise getTortoiseById(UUID id) {
        return tortoiseRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Tortoise updateTortoise(UUID id, Tortoise updatedTortoise) {
        return tortoiseRepository.findById(id)
                .map(tortoise -> {
                    tortoise.setName(updatedTortoise.getName());
                    tortoise.setSpecies(updatedTortoise.getSpecies());
                    tortoise.setAge(updatedTortoise.getAge());
                    tortoise.setHealthStatus(updatedTortoise.getHealthStatus());
                    tortoise.setHabitat(updatedTortoise.getHabitat());
                    return tortoiseRepository.save(tortoise);
                })
                .orElseThrow(NotFoundException::new);
    }


    public void deleteTortoise(UUID id) {
        tortoiseRepository.deleteById(id);
    }

    public Map<String, Double> calculateAverageAgePerHabitat(TortoiseHabitatService tortoiseHabitatService) {
        List<Tortoise> tortoises = getAllTortoises();
        Map<UUID, List<Integer>> habitatAges = new HashMap<>();

        for (Tortoise tortoise : tortoises) {
            UUID habitatId = tortoise.getHabitat().getUuid();
            habitatAges.putIfAbsent(habitatId, new ArrayList<>());
            habitatAges.get(habitatId).add(tortoise.getAge());
        }

        Map<String, Double> averageAges = new HashMap<>();
        for (Map.Entry<UUID, List<Integer>> entry : habitatAges.entrySet()) {
            UUID habitatId = entry.getKey();
            List<Integer> ages = entry.getValue();
            double sum = 0;
            for (int age : ages) {
                sum += age;
            }
            double average = sum / ages.size();
            TortoiseHabitat habitat = tortoiseHabitatService.getTortoiseHabitatByUuid(habitatId);
            String key = habitat.getUuid().toString() + " - " + habitat.getName();
            averageAges.put(key, average);
        }

        return averageAges;
    }
}
