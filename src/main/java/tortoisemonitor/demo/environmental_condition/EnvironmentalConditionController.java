package tortoisemonitor.demo.environmental_condition;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/environment")
@Tag(name = "Environmental Conditions", description = "Operations related to Environmental Conditions")
public class EnvironmentalConditionController {

    @Operation(summary = "Log new environmental conditions", description = "Log new environmental conditions")
    @PostMapping
    public EnvironmentalCondition logEnvironmentalCondition(@RequestBody EnvironmentalCondition environmentalCondition) {
        return new EnvironmentalCondition(); // Placeholder return
    }

    @Operation(summary = "Get all environmental conditions", description = "Get a list of all environmental conditions")
    @GetMapping
    public List<EnvironmentalCondition> getAllEnvironmentalConditions() {
        return new ArrayList<>(); // Placeholder return
    }

    @Operation(summary = "Get environmental conditions by ID", description = "Get details of a specific environmental condition")
    @GetMapping("/{id}")
    public EnvironmentalCondition getEnvironmentalConditionById(@PathVariable Long id) {
        return new EnvironmentalCondition(); // Placeholder return
    }

    @Operation(summary = "Update environmental conditions", description = "Update details of an existing environmental condition")
    @PutMapping("/{id}")
    public EnvironmentalCondition updateEnvironmentalCondition(@PathVariable Long id, @RequestBody EnvironmentalCondition environmentalCondition) {
        return new EnvironmentalCondition(); // Placeholder return
    }

    @Operation(summary = "Delete environmental conditions", description = "Delete a specific environmental condition")
    @DeleteMapping("/{id}")
    public void deleteEnvironmentalCondition(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}
