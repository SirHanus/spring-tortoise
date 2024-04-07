package tortoisemonitor.demo.tortoise;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tortoises")
@Tag(name = "Tortoise Management", description = "Operations related to Tortoise Management")
public class TortoiseController {

    @Operation(summary = "Create a new tortoise profile", description = "Create a new tortoise profile", responses = {
            @ApiResponse(responseCode = "201", description = "Tortoise created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tortoise.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Tortoise already exists")
    })
    @PostMapping
    public Tortoise createTortoise(@RequestBody Tortoise tortoise) {
        return new Tortoise(); // Placeholder return
    }

    @Operation(summary = "Get a list of all tortoises", description = "Get a list of all tortoises")
    @GetMapping
    public List<Tortoise> getAllTortoises() {
        return new ArrayList<>(); // Placeholder return
    }

    @Operation(summary = "Get a tortoise by ID", description = "Get details of a specific tortoise by ID")
    @GetMapping("/{id}")
    public Tortoise getTortoiseById(@PathVariable Long id) {
        return new Tortoise(); // Placeholder return
    }

    @Operation(summary = "Update a tortoise profile", description = "Update details of an existing tortoise profile")
    @PutMapping("/{id}")
    public Tortoise updateTortoise(@PathVariable Long id, @RequestBody Tortoise tortoise) {
        return new Tortoise(); // Placeholder return
    }

    @Operation(summary = "Delete a tortoise profile", description = "Delete a specific tortoise profile")
    @DeleteMapping("/{id}")
    public void deleteTortoise(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}
