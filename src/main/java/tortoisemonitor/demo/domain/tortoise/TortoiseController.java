package tortoisemonitor.demo.domain.tortoise;

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
@RequestMapping("/tortoises")
@Tag(name = "Tortoise Management", description = "Operations related to Tortoise Management")
public class TortoiseController {

    TortoiseService tortoiseService;

    @Autowired
    public TortoiseController(TortoiseService tortoiseService) {
        this.tortoiseService = tortoiseService;
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Create a new tortoise profile",
            description = "Create a new tortoise profile. This endpoint allows for the creation of a new tortoise profile in the system.",
            responses = {@ApiResponse(responseCode = "201", description = "Tortoise created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tortoise.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "409", description = "Tortoise already exists",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Tortoise createTortoise(@RequestBody Tortoise tortoise) {
         return tortoiseService.createTortoise(tortoise);
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get a list of all tortoises",
            description = "Retrieves a list of all tortoise profiles stored in the system.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))})
    @ResponseStatus(HttpStatus.OK) // 200
    public List<Tortoise> getAllTortoises() {
        return tortoiseService.getAllTortoises();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get a tortoise by ID",
            description = "Retrieves details of a specific tortoise by their ID.",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully retrieved tortoise details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tortoise.class))),
                    @ApiResponse(responseCode = "404", description = "Tortoise not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK) // 200
    public Tortoise getTortoiseById(@PathVariable UUID id) {
        return tortoiseService.getTortoiseById(id);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Update a tortoise profile",
            description = "Updates details of an existing tortoise profile.",
            responses = {@ApiResponse(responseCode = "200", description = "Tortoise updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tortoise.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tortoise not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK) // 200
    public Tortoise updateTortoise(@PathVariable UUID id, @RequestBody Tortoise tortoise) {
        return tortoiseService.updateTortoise(id, tortoise);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Delete a tortoise profile",
            description = "Deletes a specific tortoise profile based on the ID provided.",
            responses = {@ApiResponse(responseCode = "200", description = "Tortoise deleted successfully",
                    content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tortoise not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK) // 200
    public void deleteTortoise(@PathVariable UUID id) {
        tortoiseService.deleteTortoise(id);
    }
}
