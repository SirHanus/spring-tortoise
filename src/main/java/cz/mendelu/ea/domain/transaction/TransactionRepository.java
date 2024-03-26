package cz.mendelu.ea.domain.transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

    int countByAmountLessThan(double amount);

    Iterable<Transaction> findBySourceAccount_Owner_NameOrTargetAccount_Owner_Name(String name1, String name2);

    @Query("""
        SELECT COUNT(t.id) FROM Transaction t
        WHERE t.sourceAccount = t.targetAccount
    """)
    int countTransactionsWithSameSourceAndTargetAccount();

}
