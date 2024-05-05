package cz.mendelu.ea.domain.statistics;

import cz.mendelu.ea.domain.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class StatisticsServiceUnitTest {

    @ParameterizedTest
    @MethodSource("testGetNamesOfUsersParameters")
    public void testGetNamesOfUsers(List<User> users, List<String> expected) {
        // given
        StatisticsService statisticsService = new StatisticsService(null, null, null);

        // when
        List<String> result = statisticsService.getNamesOfUsers(users);

        // then results equals to expeected in ayn order
        assertThat(result, containsInAnyOrder(expected.toArray()));
    }

    private static Stream<Arguments> testGetNamesOfUsersParameters() {
        return Stream.of(
                // two users
                Arguments.of(
                        Arrays.asList(new User("Ivo", "ivo"), new User("Pepa", "pepa")),
                        Arrays.asList("Ivo", "Pepa")
                ),
                // one user
                Arguments.of(
                        Arrays.asList(new User("Ivo", "ivo")),
                        Arrays.asList("Ivo")
                ),
                // no users
                Arguments.of(
                        Arrays.asList(),
                        Arrays.asList()
                )
        );
    }
}
