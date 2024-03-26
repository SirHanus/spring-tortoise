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

}