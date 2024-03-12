package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
