package tortoisemonitor.demo.activity_log;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activityLogs")
@Api(tags = "Activity Logs")
public class ActivityLogController {

    @ApiOperation(value = "Log a new activity for a tortoise")
    @PostMapping
    public ResponseEntity<ActivityLog> logActivity(@RequestBody ActivityLog activityLog) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Get a list of all activities")
    @GetMapping
    public ResponseEntity<List<ActivityLog>> getAllActivities() {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Get an activity log by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityById(@PathVariable Long id) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Update an activity log entry")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityLog> updateActivity(@PathVariable Long id, @RequestBody ActivityLog activityLog) {
        // TBD: Implementation goes here
    }

    @ApiOperation(value = "Delete an activity log entry")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}

