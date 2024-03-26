package cz.mendelu.ea.domain.statistics;

import cz.mendelu.ea.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatisticsServiceUnitTest {

    @Test
    public void testGetNamesOfUsers() {
        StatisticsService service = new StatisticsService(null, null, null);


        User user1 = new User("Ivo", "Ivo");
        User user2 = new User("Kuba", "Kuba");
        List<String> results = service.getNamesOfUsers(List.of(user1, user2));
        assertThat(results, containsInAnyOrder("Ivo", "Kuba"));
    }
}
