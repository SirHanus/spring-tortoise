package tortoisemonitor.demo.domain.environmental_condition;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/environment")
@Tag(name = "Environmental Conditions", description = "Operations related to Environmental Conditions")
@Validated
public class EnvironmentalConditionController {

    private final EnvironmentalConditionService environmentalConditionService;
    private final TortoiseHabitatService tortoiseHabitatService;

    @Autowired
    public EnvironmentalConditionController(EnvironmentalConditionService environmentalConditionService, TortoiseHabitatService tortoiseHabitatService) {
        this.environmentalConditionService = environmentalConditionService;
        this.tortoiseHabitatService = tortoiseHabitatService;
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Log new environmental conditions",
            description = "Log new environmental conditions. If no ID is specified it links to the newest tortoise habitat in DB",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Environmental condition logged successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnvironmentalConditionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    public EnvironmentalConditionResponse logEnvironmentalCondition(@Valid @RequestBody EnvironmentalConditionRequest environmentalConditionRequest) {
        EnvironmentalCondition environmentalCondition = new EnvironmentalCondition();
        environmentalConditionRequest.toEnvironmentalCondition(environmentalCondition, tortoiseHabitatService);
        EnvironmentalCondition createdEnvironmentalCondition = environmentalConditionService.createEnvironmentalCondition(environmentalCondition);

        EnvironmentalConditionResponse environmentalConditionResponse = new EnvironmentalConditionResponse();
        environmentalConditionResponse.fromEnvironmentalConditionResponse(createdEnvironmentalCondition);
        return environmentalConditionResponse;
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get all environmental conditions",
            description = "Get a list of all environmental conditions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnvironmentalConditionResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public List<EnvironmentalConditionResponse> getAllEnvironmentalConditions() {
        List<EnvironmentalConditionResponse> environmentalConditionResponses = new ArrayList<>();
        environmentalConditionService.getAllEnvironmentalConditions().forEach(x -> {
            EnvironmentalConditionResponse environmentalConditionResponse = new EnvironmentalConditionResponse();
            environmentalConditionResponse.fromEnvironmentalConditionResponse(x);
            environmentalConditionResponses.add(environmentalConditionResponse);
        });
        return environmentalConditionResponses;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get environmental conditions by ID",
            description = "Get details of a specific environmental condition",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved environmental condition details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnvironmentalConditionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Environmental condition not found",
                            content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public EnvironmentalConditionResponse getEnvironmentalConditionById(@PathVariable UUID id) {
        EnvironmentalCondition environmentalCondition = environmentalConditionService.getEnvironmentalConditionById(id);
        EnvironmentalConditionResponse environmentalConditionResponse = new EnvironmentalConditionResponse();
        environmentalConditionResponse.fromEnvironmentalConditionResponse(environmentalCondition);
        return environmentalConditionResponse;
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Update environmental conditions",
            description = "Update details of an existing environmental condition",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Environmental condition updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnvironmentalConditionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Environmental condition not found",
                            content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public EnvironmentalConditionResponse updateEnvironmentalCondition(@Valid @PathVariable UUID id, @Valid @RequestBody EnvironmentalConditionRequest environmentalConditionRequest) {
        EnvironmentalCondition environmentalCondition = new EnvironmentalCondition();
        environmentalConditionRequest.toEnvironmentalCondition(environmentalCondition, tortoiseHabitatService);
        environmentalConditionService.updateEnvironmentalCondition(id, environmentalCondition);

        EnvironmentalConditionResponse environmentalConditionResponse = new EnvironmentalConditionResponse();
        environmentalConditionResponse.fromEnvironmentalConditionResponse(environmentalCondition);
        return environmentalConditionResponse;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Delete environmental conditions",
            description = "Delete a specific environmental condition",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Environmental condition deleted successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Environmental condition not found",
                            content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public void deleteEnvironmentalCondition(@Valid @PathVariable UUID id) {
        environmentalConditionService.deleteEnvironmentalCondition(id);
    }
}
