package cz.mendelu.ea.domain.user;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findTop3ByOrderByAccounts_balanceDesc();


}