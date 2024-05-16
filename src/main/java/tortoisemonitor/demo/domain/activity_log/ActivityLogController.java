package tortoisemonitor.demo.domain.activity_log;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/activityLogs")
@Tag(name = "Activity Logs", description = "Operations related to Activity Logs")
@Validated
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @Autowired
    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

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
    public ActivityLogResponse logActivity(@RequestBody ActivityLogRequest activityLogRequest) {
        ActivityLogResponse activityLogResponse = new ActivityLogResponse();
        activityLogResponse.fromActivityLog(activityLogService.createActivityLog(activityLogRequest));

        return activityLogService.createActivityLog(activityLog);
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
        return activityLogService.getAllActivityLogs(); // Placeholder return
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
    public ActivityLog getActivityById(@PathVariable UUID id) {
        return activityLogService.getActivityLogById(id); // Placeholder return
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
    public ActivityLog updateActivity(@PathVariable UUID id, @RequestBody ActivityLog activityLog) {
        return activityLogService.updateActivityLog(id,activityLog);
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
    public void deleteActivity(@PathVariable UUID id) {
        activityLogService.deleteActivityLog(id);
    }
}
