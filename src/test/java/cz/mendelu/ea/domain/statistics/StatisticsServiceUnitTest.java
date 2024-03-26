package cz.mendelu.ea.domain.statistics;

import cz.mendelu.ea.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatisticsServiceUnitTest {

    @ParameterizedTest
    @MethodSource("testGetNamesOfUsersParameters")
    public void testGetNamesOfUsers(List<User> users, List<String> expectedNames) {
        StatisticsService service = new StatisticsService(null, null, null);


        List<String> results = service.getNamesOfUsers(users);
        assertThat(results, containsInAnyOrder(expectedNames.toArray()));
    }

    private static Stream<Arguments> testGetNamesOfUsersParameters() {
        return Stream.of(
                Arguments.of(List.of(
                        new User("Ivo", "Ivo"),
                        new User("Kuba", "Kuba")), List.of("Ivo", "Kuba")),
                Arguments.of(
                        List.of(
                                new User("Petr", "Ivo"),
                                new User("Honza", "Kuba")), List.of("Petr", "Honza")),
                Arguments.of(
                        List.of()
                        , List.of())
        );
    }
}
