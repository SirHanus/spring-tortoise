package cz.mendelu.ea.domain.loan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Data
public class LoanResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Min(0)
    private Double amount = 0.0;

    @NotNull
    @Min(0)
    private double interestRate = 0.0;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @Min(1)
    private int period;

    public LoanResponse(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getAmount();
        this.interestRate = loan.getInterestRate();
        this.startDate = loan.getStartDate();
        this.period = loan.getPeriod();
    }
}
