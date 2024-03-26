package cz.mendelu.ea.domain.statistics;

import cz.mendelu.ea.domain.account.AccountRepository;
import cz.mendelu.ea.domain.transaction.Transaction;
import cz.mendelu.ea.domain.transaction.TransactionRepository;
import cz.mendelu.ea.domain.user.User;
import cz.mendelu.ea.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StatisticsService {

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    public StatisticsService(UserRepository userRepository, TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Long> getIdsOfUsers(Iterable<User> users) {
        List<Long> results = new ArrayList<>();
        users.forEach(u -> results.add(u.getId()));
        return results;
    }

    public List<String> getNamesOfUsers(Iterable<User> users) {
        List<String> results = new ArrayList<>();
        users.forEach(u -> results.add(u.getName()));
        return results;
    }

    public List<UUID> getIdsOfTransactions(Iterable<Transaction> transactions) {
        List<UUID> results = new ArrayList<>();
        transactions.forEach(t -> results.add(t.getId()));
        return results;
    }

    public Statistics getStatistics() {
        return new Statistics(
                accountRepository.countByBalanceGreaterThan(150.0),
                getNamesOfUsers(userRepository.findTop3ByOrderByAccounts_balanceDesc()),
                accountRepository.countByAverageOutgoingTransactionAmountGreaterThan(100.0),
                transactionRepository.countByAmountLessThan(100.0),
                getIdsOfUsers(userRepository.findByName("Ivo")),
                userRepository.countByNameLikeIgnoreCase("I%"),
                getNamesOfUsers(userRepository.findByOwnedAccounts_BalanceGreaterThan(150.0)),
                getIdsOfTransactions(transactionRepository.findBySourceAccount_Owner_NameOrTargetAccount_Owner_Name("Ivo", "Ivo")),
                transactionRepository.countTransactionsWithSameSourceAndTargetAccount(),
                userRepository.findUsersWithOneAccount(),
                userRepository.countAverageNumberOfOutgoingTransactions()
        );
    }

}
