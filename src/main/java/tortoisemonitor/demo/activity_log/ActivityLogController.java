package tortoisemonitor.demo.activity_log;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/activityLogs")
@Api(tags = "Activity Logs")
public class ActivityLogController {

    @ApiOperation(value = "Log a new activity for a tortoise")
    @PostMapping
    public ActivityLog logActivity(@RequestBody ActivityLog activityLog) {
        return new ActivityLog();
    }

    @ApiOperation(value = "Get a list of all activities")
    @GetMapping
    public List<ActivityLog> getAllActivities() {
        return new ArrayList<>();
    }

    @ApiOperation(value = "Get an activity log by ID")
    @GetMapping("/{id}")
    public ActivityLog getActivityById(@PathVariable Long id) {
        return new ActivityLog();
    }

    @ApiOperation(value = "Update an activity log entry")
    @PutMapping("/{id}")
    public ActivityLog updateActivity(@PathVariable Long id, @RequestBody ActivityLog activityLog) {
        return new ActivityLog();
    }

    @ApiOperation(value = "Delete an activity log entry")
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}

