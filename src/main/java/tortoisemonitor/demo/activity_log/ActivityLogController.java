package tortoisemonitor.demo.activity_log;

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
@RequestMapping("/activityLogs")
@Tag(name = "Activity Logs", description = "Operations related to Activity Logs")
public class ActivityLogController {

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Log a new activity for a tortoise",
            description = "Log a new activity for a tortoise.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Activity logged successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityLog.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityLog logActivity(@RequestBody ActivityLog activityLog) {
        return new ActivityLog(); // Placeholder return
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get a list of all activities",
            description = "Get a list of all activities recorded for tortoises.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)))})
    @ResponseStatus(HttpStatus.OK)
    public List<ActivityLog> getAllActivities() {
        return new ArrayList<>(); // Placeholder return
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get an activity log by ID",
            description = "Get details of a specific activity log entry by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved activity log details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityLog.class))),
                    @ApiResponse(responseCode = "404", description = "Activity log not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public ActivityLog getActivityById(@PathVariable Long id) {
        return new ActivityLog(); // Placeholder return
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Update an activity log entry",
            description = "Update details of an existing activity log entry.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Activity log updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityLog.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Activity log not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public ActivityLog updateActivity(@PathVariable Long id, @RequestBody ActivityLog activityLog) {
        return new ActivityLog(); // Placeholder return
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Delete an activity log entry",
            description = "Delete a specific activity log entry by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Activity log deleted successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Activity log not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public void deleteActivity(@PathVariable Long id) {
        // TBD: Implementation goes here
    }
}
