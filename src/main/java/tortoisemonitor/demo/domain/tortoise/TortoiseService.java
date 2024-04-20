package tortoisemonitor.demo.domain.tortoise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.utils.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                .orElseThrow(() -> new RuntimeException("Tortoise not found with id " + id));
    }

    public void deleteTortoise(UUID id) {
        tortoiseRepository.deleteById(id);
    }
}
