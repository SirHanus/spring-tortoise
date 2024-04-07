package tortoisemonitor.demo.activity_log;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/activityLogs")
@Tag(name = "Activity Logs", description = "Operations related to Activity Logs")
public class ActivityLogController {

    @Operation(summary = "Log a new activity for a tortoise", description = "Log a new activity for a tortoise")
    @PostMapping
    public ActivityLog logActivity(@RequestBody ActivityLog activityLog) {
        return new ActivityLog(); // Placeholder return
    }

    @Operation(summary = "Get a list of all activities", description = "Get a list of all activities")
    @GetMapping
    public List<ActivityLog> getAllActivities() {
        return new ArrayList<>(); // Placeholder return
    }

    @Operation(summary = "Get an activity log by ID", description = "Get details of a specific activity log entry")
    @GetMapping("/{id}")
    public ActivityLog getActivityById(@PathVariable Long id) {
        return new ActivityLog(); // Placeholder return
    }

    @Operation(summary = "Update an activity log entry", description = "Update details of an existing activity log entry")
    @PutMapping("/{id}")
    public ActivityLog updateActivity(@PathVariable Long id, @RequestBody ActivityLog activityLog) {
        return new ActivityLog(); // Placeholder return
    }

    @Operation(summary = "Delete an activity log entry", description = "Delete a specific activity log entry")
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}
