package cz.mendelu.ea.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Statistics {

    int numberOfAccountsWithBalanceGreaterThan150 = 0;

    List<String> namesOf3UsersWithHighestBalance = new ArrayList<>();

    int numberOfAccountsWithAverageOutgoingTransactionGreaterThan100 = 0;

    int countOfTransactionsLessThan100 = 0;

    List<Long> idsOfUsersNamedIvo = new ArrayList<>();

    int countOfPeopleStartingWithI = 0;

    List<String> namesOfUsersWithBalanceGreaterThan150OnOwnedAccounts = new ArrayList<>();

    List<UUID> idsOfTransactionsWhereIvoIsOwner = new ArrayList<>();

    int countOfTransactionsWithSameSourceAndTargetAccount = 0;

    Iterable<Long> usersWithOneAccount = new ArrayList<>();

    double averageNumberOfOutgoingTransactionsPerUser = 0.0;

}