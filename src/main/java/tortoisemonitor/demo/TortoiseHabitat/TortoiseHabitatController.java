package tortoisemonitor.demo.TortoiseHabitat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tortoiseHabitats")
@Tag(name = "Tortoise Habitat Management", description = "Operations related to Tortoise Habitat Management")
public class TortoiseHabitatController {

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Create a new tortoise habitat",
            description = "Create a new tortoise habitat with specified environmental conditions and tortoises.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tortoise habitat created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseHabitat.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    public TortoiseHabitat createTortoiseHabitat(@RequestBody TortoiseHabitat tortoiseHabitat) {
        return new TortoiseHabitat(); // Placeholder return
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get all tortoise habitats",
            description = "Retrieves a list of all tortoise habitats along with their environmental conditions and tortoises.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)))})
    @ResponseStatus(HttpStatus.OK)
    public List<TortoiseHabitat> getAllTortoiseHabitats() {
        return new ArrayList<>(); // Placeholder return
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    @Operation(summary = "Get a tortoise habitat by UUID",
            description = "Retrieves details of a specific tortoise habitat by its UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tortoise habitat details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseHabitat.class))),
                    @ApiResponse(responseCode = "404", description = "Tortoise habitat not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public TortoiseHabitat getTortoiseHabitatByUuid(@PathVariable String uuid) {
        return new TortoiseHabitat(); // Placeholder return
    }

    @PutMapping(value = "/{uuid}", produces = "application/json")
    @Operation(summary = "Update a tortoise habitat",
            description = "Updates details of an existing tortoise habitat by its UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tortoise habitat updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TortoiseHabitat.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tortoise habitat not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public TortoiseHabitat updateTortoiseHabitat(@PathVariable String uuid, @RequestBody TortoiseHabitat tortoiseHabitat) {
        return new TortoiseHabitat(); // Placeholder return
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
    public void deleteTortoiseHabitat(@PathVariable String uuid) {
        // TBD: Implementation goes here
    }
}
