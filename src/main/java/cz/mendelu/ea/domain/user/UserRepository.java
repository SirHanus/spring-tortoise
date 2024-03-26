package cz.mendelu.ea.domain.user;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"accounts", "ownedAccounts"})//, "accounts.users", "ownedAccounts.users"})
    Iterable<User> findAll();

    Iterable<User> findTop3ByOrderByAccounts_balanceDesc();

    Iterable<User> findByName(String name);

    int countByNameLikeIgnoreCase(String name);

    Iterable<User> findByOwnedAccounts_BalanceGreaterThan(double balance);

    @Query("""
            SELECT u.id FROM User u
                JOIN u.accounts a
                GROUP BY u.id
                HAVING COUNT(a) = 1
            """)
    Iterable<Long> findUsersWithOneAccount();

    @Query("""
            SELECT AVG(subquery.count) FROM (
                SELECT COUNT(*) as count FROM User u
                    JOIN u.ownedAccounts a
                    JOIN a.outgoingTransactions t
                    GROUP BY u.id
            ) as subquery
            """)
    double countAverageNumberOfOutgoingTransactions();



}