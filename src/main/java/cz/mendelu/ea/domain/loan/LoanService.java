package cz.mendelu.ea.domain.loan;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanService {
    private final LoanRepository repository;

    public LoanService(LoanRepository repository) {
        this.repository = repository;
    }

    public List<Loan> getLoans() {
        List<Loan> loans = new ArrayList<>();
        repository.findAll().forEach(loans::add);
        return loans;
    }

    public Loan createLoan(Loan loan) {
        return repository.save(loan);
    }

    public Optional<Loan> getLoan(UUID id) {
        return repository.findById(id);
    }

    public Loan updateLoan(UUID id, Loan loanDetails) {
        loanDetails.setId(id);
        return repository.save(loanDetails);
    }

    public void deleteLoan(UUID id) {
        repository.deleteById(id);
    }
}



