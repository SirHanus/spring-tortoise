package tortoisemonitor.demo.environmental_condition;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/environment")
@Api(tags = "Environmental Conditions")
public class EnvironmentalConditionController {

    @ApiOperation(value = "Log new environmental conditions")
    @PostMapping
    public EnvironmentalCondition logEnvironmentalCondition(@RequestBody EnvironmentalCondition environmentalCondition) {
        return new EnvironmentalCondition();
    }

    @ApiOperation(value = "Get all environmental conditions")
    @GetMapping
    public List<EnvironmentalCondition> getAllEnvironmentalConditions() {
        return new ArrayList<>();
    }

    @ApiOperation(value = "Get environmental conditions by ID")
    @GetMapping("/{id}")
    public EnvironmentalCondition getEnvironmentalConditionById(@PathVariable Long id) {
        return new EnvironmentalCondition();
    }

    @ApiOperation(value = "Update environmental conditions")
    @PutMapping("/{id}")
    public EnvironmentalCondition updateEnvironmentalCondition(@PathVariable Long id, @RequestBody EnvironmentalCondition environmentalCondition) {
        return new EnvironmentalCondition();
    }

    @ApiOperation(value = "Delete environmental conditions")
    @DeleteMapping("/{id}")
    public void deleteEnvironmentalCondition(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}
