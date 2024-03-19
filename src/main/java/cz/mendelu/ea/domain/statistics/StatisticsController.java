package cz.mendelu.ea.domain.statistics;

import cz.mendelu.ea.utils.response.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
@Validated
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "", produces = "application/json")
    public ObjectResponse<Statistics> getStatistics() {
        var statistics = statisticsService.getStatistics();
        return new ObjectResponse<>(statistics, 1);
    }

}
