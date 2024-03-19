package cz.mendelu.ea.domain.statistics;

import cz.mendelu.ea.domain.account.AccountRepository;
import cz.mendelu.ea.domain.transaction.TransactionRepository;
import cz.mendelu.ea.domain.user.UserRepository;
import org.springframework.stereotype.Service;

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

    public Statistics getStatistics() {
        return new Statistics(12);
    }

}
