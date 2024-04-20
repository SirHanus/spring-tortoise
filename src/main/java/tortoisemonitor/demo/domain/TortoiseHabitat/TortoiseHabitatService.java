package tortoisemonitor.demo.domain.TortoiseHabitat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tortoisemonitor.demo.utils.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TortoiseHabitatService {

    private final TortoiseHabitatRepository tortoiseHabitatRepository;

    @Autowired
    public TortoiseHabitatService(TortoiseHabitatRepository tortoiseHabitatRepository) {
        this.tortoiseHabitatRepository = tortoiseHabitatRepository;
    }

    public TortoiseHabitat createTortoiseHabitat(TortoiseHabitat tortoiseHabitat) {
        return tortoiseHabitatRepository.save(tortoiseHabitat);
    }

    public List<TortoiseHabitat> getAllTortoiseHabitats() {
        List<TortoiseHabitat> habitats = new ArrayList<>();
        tortoiseHabitatRepository.findAll().forEach(habitats::add);
        return habitats;
    }

    public TortoiseHabitat getTortoiseHabitatByUuid(UUID uuid) {
        return tortoiseHabitatRepository.findById(uuid).orElseThrow(NotFoundException::new);
    }

    public TortoiseHabitat updateTortoiseHabitat(UUID uuid, TortoiseHabitat updatedHabitat) {
        return tortoiseHabitatRepository.findById(uuid)
                .map(habitat -> {
                    habitat.setName(updatedHabitat.getName());
                    // Potentially update linked tortoises and environmental conditions if needed
                    return tortoiseHabitatRepository.save(habitat);
                })
                .orElseThrow(() -> new RuntimeException("Tortoise habitat not found with uuid " + uuid));
    }

    public void deleteTortoiseHabitat(UUID uuid) {
        tortoiseHabitatRepository.deleteById(uuid);
    }
}
