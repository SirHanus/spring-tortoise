package tortoisemonitor.demo.domain.TortoiseHabitat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionService;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tortoiseHabitats")
@Tag(name = "Tortoise Habitat Management", description = "Operations related to Tortoise Habitat Management")
public class TortoiseHabitatController {

    public final TortoiseHabitatService tortoiseHabitatService;
    private final TortoiseService tortoiseService;
    private final EnvironmentalConditionService environmentalConditionService;

    @Autowired
    public TortoiseHabitatController(TortoiseHabitatService tortoiseHabitatService, TortoiseService tortoiseService, EnvironmentalConditionService environmentalConditionService) {
        this.tortoiseHabitatService = tortoiseHabitatService;
        this.tortoiseService = tortoiseService;
        this.environmentalConditionService = environmentalConditionService;
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Create a new tortoise habitat",
            description = "Create a new tortoise habitat with specified environmental conditions and tortoises.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tortoise habitat created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseHabitatResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    public TortoiseHabitatResponse createTortoiseHabitat(@RequestBody TortoiseHabitatRequest tortoiseHabitatRequest) {
        TortoiseHabitat tortoiseHabitat = new TortoiseHabitat();
        tortoiseHabitatRequest.toTortoiseHabitat(tortoiseHabitat);
        tortoiseHabitat = tortoiseHabitatService.createTortoiseHabitat(tortoiseHabitat);
        TortoiseHabitatResponse tortoiseHabitatResponse = new TortoiseHabitatResponse();
        tortoiseHabitatResponse.fromTortoiseHabitat(tortoiseHabitat);
        return tortoiseHabitatResponse;
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get all tortoise habitats",
            description = "Retrieves a list of all tortoise habitats along with their environmental conditions and tortoises.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseHabitatResponse.class)))})
    @ResponseStatus(HttpStatus.OK)
    public List<TortoiseHabitatResponse> getAllTortoiseHabitats() {
        return tortoiseHabitatService.getAllTortoiseHabitats().stream().map(tortoiseHabitat -> {
            TortoiseHabitatResponse tortoiseHabitatResponse = new TortoiseHabitatResponse();
            tortoiseHabitatResponse.fromTortoiseHabitat(tortoiseHabitat);
            return tortoiseHabitatResponse;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    @Operation(summary = "Get a tortoise habitat by UUID",
            description = "Retrieves details of a specific tortoise habitat by its UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tortoise habitat details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseHabitatResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Tortoise habitat not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public TortoiseHabitatResponse getTortoiseHabitatByUuid(@PathVariable UUID uuid) {
        TortoiseHabitat tortoiseHabitat = tortoiseHabitatService.getTortoiseHabitatByUuid(uuid);
        TortoiseHabitatResponse tortoiseHabitatResponse = new TortoiseHabitatResponse();
        tortoiseHabitatResponse.fromTortoiseHabitat(tortoiseHabitat);
        return tortoiseHabitatResponse;
    }

    @PutMapping(value = "/{uuid}", produces = "application/json")
    @Operation(summary = "Update a tortoise habitat",
            description = "Updates details of an existing tortoise habitat by its UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tortoise habitat updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseHabitatResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tortoise habitat not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public TortoiseHabitatResponse updateTortoiseHabitat(@PathVariable UUID uuid, @RequestBody TortoiseHabitatRequest tortoiseHabitatRequest) {
        TortoiseHabitat tortoiseHabitat = new TortoiseHabitat();
        tortoiseHabitatRequest.toTortoiseHabitat(tortoiseHabitat);
        tortoiseHabitatService.updateTortoiseHabitat(uuid, tortoiseHabitat);
        TortoiseHabitatResponse tortoiseHabitatResponse = new TortoiseHabitatResponse();
        tortoiseHabitatResponse.fromTortoiseHabitat(tortoiseHabitat);
        return tortoiseHabitatResponse;
    }

    @DeleteMapping(value = "/{uuid}", produces = "application/json")
    @Operation(summary = "Delete a tortoise habitat",
            description = "Deletes a specific tortoise habitat by its UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tortoise habitat deleted successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tortoise habitat not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public void deleteTortoiseHabitat(@PathVariable UUID uuid) {
        tortoiseHabitatService.deleteTortoiseHabitat(uuid);
    }
}
