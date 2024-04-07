package tortoisemonitor.demo.environmental_condition;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/environment")
@Api(tags = "Environmental Conditions")
public class EnvironmentalConditionController {

    @ApiOperation(value = "Log new environmental conditions")
    @PostMapping
    public ResponseEntity<EnvironmentalCondition> logEnvironmentalCondition(@RequestBody EnvironmentalCondition environmentalCondition) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Get all environmental conditions")
    @GetMapping
    public ResponseEntity<List<EnvironmentalCondition>> getAllEnvironmentalConditions() {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Get environmental conditions by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentalCondition> getEnvironmentalConditionById(@PathVariable Long id) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Update environmental conditions")
    @PutMapping("/{id}")
    public ResponseEntity<EnvironmentalCondition> updateEnvironmentalCondition(@PathVariable Long id, @RequestBody EnvironmentalCondition environmentalCondition) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Delete environmental conditions")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvironmentalCondition(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}
