package cz.mendelu.ea.domain.loan;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LoanRepository extends CrudRepository<Loan, UUID> {
}
