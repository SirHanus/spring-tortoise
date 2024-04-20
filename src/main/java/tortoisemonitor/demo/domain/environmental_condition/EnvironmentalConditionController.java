package tortoisemonitor.demo.domain.environmental_condition;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/environment")
@Tag(name = "Environmental Conditions", description = "Operations related to Environmental Conditions")
public class EnvironmentalConditionController {

    private final EnvironmentalConditionService environmentalConditionService;

    @Autowired
    public EnvironmentalConditionController(EnvironmentalConditionService environmentalConditionService) {
        this.environmentalConditionService = environmentalConditionService;
    }


    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Log new environmental conditions",
            description = "Log new environmental conditions",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Environmental condition logged successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnvironmentalCondition.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    public EnvironmentalCondition logEnvironmentalCondition(@RequestBody EnvironmentalCondition environmentalCondition) {
        return environmentalConditionService.createEnvironmentalCondition(environmentalCondition);
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get all environmental conditions",
            description = "Get a list of all environmental conditions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)))})
    @ResponseStatus(HttpStatus.OK)
    public List<EnvironmentalCondition> getAllEnvironmentalConditions() {
        List<EnvironmentalCondition> environmentalConditions = new ArrayList<>();
        environmentalConditionService.getAllEnvironmentalConditions().forEach(environmentalConditions::add);
        return environmentalConditions;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get environmental conditions by ID",
            description = "Get details of a specific environmental condition",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved environmental condition details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnvironmentalCondition.class))),
                    @ApiResponse(responseCode = "404", description = "Environmental condition not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public EnvironmentalCondition getEnvironmentalConditionById(@PathVariable UUID id) {
        return environmentalConditionService.getEnvironmentalConditionById(id);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Update environmental conditions",
            description = "Update details of an existing environmental condition",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Environmental condition updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnvironmentalCondition.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Environmental condition not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public EnvironmentalCondition updateEnvironmentalCondition(@PathVariable UUID id, @RequestBody EnvironmentalCondition environmentalCondition) {
        return environmentalConditionService.updateEnvironmentalCondition(id, environmentalCondition);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Delete environmental conditions",
            description = "Delete a specific environmental condition",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Environmental condition deleted successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Environmental condition not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public void deleteEnvironmentalCondition(@PathVariable UUID id) {
        environmentalConditionService.deleteEnvironmentalCondition(id);
    }
}
