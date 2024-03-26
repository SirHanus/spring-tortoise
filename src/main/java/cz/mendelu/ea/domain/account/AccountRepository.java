package cz.mendelu.ea.domain.account;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Override
    @EntityGraph(attributePaths = {"outgoingTransactions", "incomingTransactions"})
    @NonNull
    Iterable<Account> findAll();

    int countByBalanceGreaterThan(double balance);

    // return accounts where average amount of outgoing transaction is greater than the given number
    @Query("""
            SELECT COUNT(*) FROM (
                SELECT a.id AS id FROM Account a
                    JOIN a.outgoingTransactions t
                    GROUP BY a.id
                    HAVING AVG(t.amount) > ?1
            ) as subquery
            """)
    int countByAverageOutgoingTransactionAmountGreaterThan(double amount);

}
