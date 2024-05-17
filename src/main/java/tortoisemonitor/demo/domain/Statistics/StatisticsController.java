package tortoisemonitor.demo.domain.Statistics;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;
import tortoisemonitor.demo.domain.activity_log.ActivityType;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionService;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.time.DayOfWeek;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@Tag(name = "Statistics", description = "Operations related to statistical calculations")
public class StatisticsController {

    private final EnvironmentalConditionService environmentalConditionService;
    private final ActivityLogService activityLogService;
    private final TortoiseService tortoiseService;
    private final TortoiseHabitatService tortoiseHabitatService;

    @Autowired
    public StatisticsController(EnvironmentalConditionService environmentalConditionService, ActivityLogService activityLogService, TortoiseService tortoiseService, TortoiseHabitatService tortoiseHabitatService) {
        this.environmentalConditionService = environmentalConditionService;
        this.activityLogService = activityLogService;
        this.tortoiseService = tortoiseService;
        this.tortoiseHabitatService = tortoiseHabitatService;
    }

    @GetMapping("/average-temperature")
    @Operation(summary = "Get average temperature per habitat",
            description = "Calculates the average temperature for each habitat based on recorded environmental conditions. Temperature is measured in degrees Celsius.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved average temperatures",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            })
    public Map<String, Double> getAverageTemperaturePerHabitat() {
        return environmentalConditionService.calculateAverageTemperaturePerHabitat(tortoiseHabitatService);
    }

    @GetMapping("/total-activity-duration")
    @Operation(summary = "Get total activity duration per tortoise",
            description = "Calculates the total duration of all activities for each tortoise. Duration is measured in minutes.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved total activity durations",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            })
    public Map<String, Long> getTotalActivityDurationPerTortoise() {
        return activityLogService.calculateTotalActivityDurationPerTortoise(tortoiseService);
    }

    @GetMapping("/most-common-activity")
    @Operation(summary = "Get most common activity type per tortoise",
            description = "Identifies the most common activity type for each tortoise.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved most common activity types",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            })
    public Map<String, ActivityType> getMostCommonActivityTypePerTortoise() {
        return activityLogService.calculateMostCommonActivityTypePerTortoise(tortoiseService);
    }

    @GetMapping("/average-age")
    @Operation(summary = "Get average age per habitat",
            description = "Calculates the average age of tortoises in each habitat. Age is measured in years.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved average ages",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            })
    public Map<String, Double> getAverageAgePerHabitat() {
        return tortoiseService.calculateAverageAgePerHabitat(tortoiseHabitatService);
    }

    @GetMapping("/activity-distribution")
    @Operation(summary = "Get activity distribution per day of the week",
            description = "Calculates the number of activities per day of the week.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved activity distribution",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            })
    public Map<DayOfWeek, Long> getActivityDistributionPerDay() {
        return activityLogService.calculateActivityDistributionPerDay();
    }
}
