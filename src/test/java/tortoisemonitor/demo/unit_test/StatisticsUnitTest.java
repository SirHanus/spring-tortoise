package tortoisemonitor.demo.unit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tortoisemonitor.demo.domain.Statistics.StatisticsController;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatService;
import tortoisemonitor.demo.domain.activity_log.ActivityLogService;
import tortoisemonitor.demo.domain.activity_log.ActivityType;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionService;
import tortoisemonitor.demo.domain.tortoise.TortoiseService;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class StatisticsUnitTest {

    @Mock
    private EnvironmentalConditionService environmentalConditionService;

    @Mock
    private ActivityLogService activityLogService;

    @Mock
    private TortoiseService tortoiseService;

    @Mock
    private TortoiseHabitatService tortoiseHabitatService;

    @InjectMocks
    private StatisticsController statisticsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAverageTemperaturePerHabitat() {
        // Given
        Map<String, Double> expectedTemperatures = new HashMap<>();
        expectedTemperatures.put("11111111-1111-1111-1111-111111111111 - Habitat1", 22.00);
        expectedTemperatures.put("22222222-2222-2222-2222-222222222222 - Habitat2", 24.00);
        when(environmentalConditionService.calculateAverageTemperaturePerHabitat(tortoiseHabitatService)).thenReturn(expectedTemperatures);

        // When
        Map<String, Double> result = statisticsController.getAverageTemperaturePerHabitat();

        // Then
        assertThat(result, is(expectedTemperatures));
        verify(environmentalConditionService).calculateAverageTemperaturePerHabitat(tortoiseHabitatService);
    }

    @Test
    public void testGetTotalActivityDurationPerTortoise() {
        // Given
        Map<String, Long> expectedDurations = new HashMap<>();
        expectedDurations.put("33333333-3333-3333-3333-333333333333 - Tortoise1", 60L);
        expectedDurations.put("44444444-4444-4444-4444-444444444444 - Tortoise2", 60L);
        expectedDurations.put("55555555-5555-5555-5555-555555555555 - Tortoise3", 30L);
        when(activityLogService.calculateTotalActivityDurationPerTortoise(tortoiseService)).thenReturn(expectedDurations);

        // When
        Map<String, Long> result = statisticsController.getTotalActivityDurationPerTortoise();

        // Then
        assertThat(result, is(expectedDurations));
        verify(activityLogService).calculateTotalActivityDurationPerTortoise(tortoiseService);
    }

    @Test
    public void testGetMostCommonActivityTypePerTortoise() {
        // Given
        Map<String, ActivityType> expectedActivities = new HashMap<>();
        expectedActivities.put("33333333-3333-3333-3333-333333333333 - Tortoise1", ActivityType.FEEDING);
        expectedActivities.put("44444444-4444-4444-4444-444444444444 - Tortoise2", ActivityType.BASKING);
        expectedActivities.put("55555555-5555-5555-5555-555555555555 - Tortoise3", ActivityType.SLEEPING);
        when(activityLogService.calculateMostCommonActivityTypePerTortoise(tortoiseService)).thenReturn(expectedActivities);

        // When
        Map<String, ActivityType> result = statisticsController.getMostCommonActivityTypePerTortoise();

        // Then
        assertThat(result, is(expectedActivities));
        verify(activityLogService).calculateMostCommonActivityTypePerTortoise(tortoiseService);
    }

    @Test
    public void testGetAverageAgePerHabitat() {
        // Given
        Map<String, Double> expectedAges = new HashMap<>();
        expectedAges.put("11111111-1111-1111-1111-111111111111 - Habitat1", 3.50);
        expectedAges.put("22222222-2222-2222-2222-222222222222 - Habitat2", 3.00);
        when(tortoiseService.calculateAverageAgePerHabitat(tortoiseHabitatService)).thenReturn(expectedAges);

        // When
        Map<String, Double> result = statisticsController.getAverageAgePerHabitat();

        // Then
        assertThat(result, is(expectedAges));
        verify(tortoiseService).calculateAverageAgePerHabitat(tortoiseHabitatService);
    }

    @Test
    public void testGetActivityDistributionPerDay() {
        // Given
        Map<DayOfWeek, Long> expectedDistribution = new HashMap<>();
        expectedDistribution.put(DayOfWeek.MONDAY, 3L);
        expectedDistribution.put(DayOfWeek.TUESDAY, 3L);
        expectedDistribution.put(DayOfWeek.WEDNESDAY, 3L);
        expectedDistribution.put(DayOfWeek.THURSDAY, 3L);
        expectedDistribution.put(DayOfWeek.FRIDAY, 3L);
        when(activityLogService.calculateActivityDistributionPerDay()).thenReturn(expectedDistribution);

        // When
        Map<DayOfWeek, Long> result = statisticsController.getActivityDistributionPerDay();

        // Then
        assertThat(result, is(expectedDistribution));
        verify(activityLogService).calculateActivityDistributionPerDay();
    }
}
