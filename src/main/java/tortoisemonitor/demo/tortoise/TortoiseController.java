package tortoisemonitor.demo.tortoise;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tortoises")
@Api(tags = "Tortoise Management")
public class TortoiseController {

    @ApiOperation(value = "Create a new tortoise profile")
    @PostMapping
    public Tortoise createTortoise(@RequestBody Tortoise tortoise) {
        return new Tortoise();
    }

    @ApiOperation(value = "Get a list of all tortoises")
    @GetMapping
    public List<Tortoise> getAllTortoises() {
        return new ArrayList<>();
    }

    @ApiOperation(value = "Get a tortoise by ID")
    @GetMapping("/{id}")
    public Tortoise getTortoiseById(@PathVariable Long id) {
        return new Tortoise();
    }

    @ApiOperation(value = "Update a tortoise profile")
    @PutMapping("/{id}")
    public Tortoise updateTortoise(@PathVariable Long id, @RequestBody Tortoise tortoise) {
        return new Tortoise();
    }

    @ApiOperation(value = "Delete a tortoise profile")
    @DeleteMapping("/{id}")
    public void deleteTortoise(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}

