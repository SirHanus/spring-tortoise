package tortoisemonitor.demo.tortoise;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tortoises")
@Api(tags = "Tortoise Management")
public class TortoiseController {

    @ApiOperation(value = "Create a new tortoise profile")
    @PostMapping
    public ResponseEntity<Tortoise> createTortoise(@RequestBody Tortoise tortoise) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Get a list of all tortoises")
    @GetMapping
    public ResponseEntity<List<Tortoise>> getAllTortoises() {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Get a tortoise by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tortoise> getTortoiseById(@PathVariable Long id) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Update a tortoise profile")
    @PutMapping("/{id}")
    public ResponseEntity<Tortoise> updateTortoise(@PathVariable Long id, @RequestBody Tortoise tortoise) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Delete a tortoise profile")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTortoise(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}

