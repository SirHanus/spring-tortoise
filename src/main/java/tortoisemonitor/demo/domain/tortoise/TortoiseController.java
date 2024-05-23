package tortoisemonitor.demo.domain.tortoise;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tortoises")
@Tag(name = "Tortoise Management", description = "Operations related to Tortoise Management")
public class TortoiseController {

    private final ActivityLogService activityLogService;
    private final TortoiseHabitatService tortoiseHabitatService;
    private final TortoiseService tortoiseService;

    @Autowired
    public TortoiseController(TortoiseService tortoiseService, ActivityLogService activityLogService, TortoiseHabitatService tortoiseHabitatService) {
        this.tortoiseService = tortoiseService;
        this.activityLogService = activityLogService;
        this.tortoiseHabitatService = tortoiseHabitatService;
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Create a new tortoise profile",
            description = "Create a new tortoise profile. This endpoint allows for the creation of a new tortoise profile in the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tortoise created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "409", description = "Tortoise already exists",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    public TortoiseResponse createTortoise(@Valid @RequestBody TortoiseRequest tortoiseRequest) {
        Tortoise tortoise = new Tortoise();
        tortoiseRequest.toTortoise(tortoise, activityLogService, tortoiseHabitatService);
        tortoiseService.createTortoise(tortoise);
        TortoiseResponse tortoiseResponse = new TortoiseResponse();
        tortoiseResponse.fromTortoise(tortoise);
        return tortoiseResponse;
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get a list of all tortoises",
            description = "Retrieves a list of all tortoise profiles stored in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseResponse.class)))})
    @ResponseStatus(HttpStatus.OK)
    public List<TortoiseResponse> getAllTortoises() {
        return tortoiseService.getAllTortoises().stream().map(tortoise -> {
            TortoiseResponse tortoiseResponse = new TortoiseResponse();
            tortoiseResponse.fromTortoise(tortoise);
            return tortoiseResponse;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get a tortoise by ID",
            description = "Retrieves details of a specific tortoise by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tortoise details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Tortoise not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public TortoiseResponse getTortoiseById(@PathVariable UUID id) {
        Tortoise tortoise = tortoiseService.getTortoiseById(id);
        TortoiseResponse tortoiseResponse = new TortoiseResponse();
        tortoiseResponse.fromTortoise(tortoise);
        return tortoiseResponse;
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Update a tortoise profile",
            description = "Updates details of an existing tortoise profile.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tortoise updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tortoise not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public TortoiseResponse updateTortoise(@Valid @PathVariable UUID id, @Valid @RequestBody TortoiseRequest tortoiseRequest) {
        Tortoise tortoise = new Tortoise();
        tortoiseRequest.toTortoise(tortoise, activityLogService, tortoiseHabitatService);
        tortoiseService.updateTortoise(id, tortoise);
        TortoiseResponse tortoiseResponse = new TortoiseResponse();
        tortoiseResponse.fromTortoise(tortoise);
        return tortoiseResponse;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Delete a tortoise profile",
            description = "Deletes a specific tortoise profile based on the ID provided.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tortoise deleted successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tortoise not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('owner')")
    public void deleteTortoise(@Valid @PathVariable UUID id) {
        tortoiseService.deleteTortoise(id);
    }
}
