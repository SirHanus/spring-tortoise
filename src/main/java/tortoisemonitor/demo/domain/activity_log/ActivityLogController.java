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
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activityLogs")
@Tag(name = "Activity Logs", description = "Operations related to Activity Logs")
@Validated
public class ActivityLogController {

    private final ActivityLogService activityLogService;
    private final TortoiseService tortoiseService;

    @Autowired
    public ActivityLogController(ActivityLogService activityLogService, TortoiseService tortoiseService) {
        this.activityLogService = activityLogService;
        this.tortoiseService = tortoiseService;
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Log a new activity for a tortoise",
            description = "Log a new activity for a tortoise. If no ID is specified it links to the newest tortoise in DB",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Activity logged successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityLogResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityLogResponse logActivity(@RequestBody ActivityLogRequest activityLogRequest) {
        ActivityLog activityLog = new ActivityLog();
        activityLogRequest.toActivityLog(activityLog, tortoiseService);
        ActivityLog createdActivityLog = activityLogService.createActivityLog(activityLog);
        ActivityLogResponse activityLogResponse = new ActivityLogResponse();
        activityLogResponse.fromActivityLog(createdActivityLog);
        return activityLogResponse;
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get a list of all activities",
            description = "Get a list of all activities recorded for tortoises.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityLogResponse.class)))})
    @ResponseStatus(HttpStatus.OK)
    public List<ActivityLogResponse> getAllActivities() {
        return activityLogService.getAllActivityLogs().stream().map(activityLog -> {
            ActivityLogResponse activityLogResponse = new ActivityLogResponse();
            activityLogResponse.fromActivityLog(activityLog);
            return activityLogResponse;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get an activity log by ID",
            description = "Get details of a specific activity log entry by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved activity log details",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityLogResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Activity log not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public ActivityLogResponse getActivityById(@PathVariable UUID id) {
        ActivityLog activityLog = activityLogService.getActivityLogById(id);
        ActivityLogResponse activityLogResponse = new ActivityLogResponse();
        activityLogResponse.fromActivityLog(activityLog);
        return activityLogResponse;
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Update an activity log entry",
            description = "Update details of an existing activity log entry.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Activity log updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityLogResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Activity log not found",
                            content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public ActivityLogResponse updateActivity(@PathVariable UUID id, @RequestBody ActivityLogRequest activityLogRequest) {
        ActivityLog activityLog = new ActivityLog();
        activityLogRequest.toActivityLog(activityLog, tortoiseService);
        ActivityLog updatedActivityLog = activityLogService.updateActivityLog(id, activityLog);

        ActivityLogResponse activityLogResponse = new ActivityLogResponse();
        activityLogResponse.fromActivityLog(updatedActivityLog);
        return activityLogResponse;
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
