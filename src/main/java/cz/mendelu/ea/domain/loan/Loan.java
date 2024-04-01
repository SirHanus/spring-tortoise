package cz.mendelu.ea.domain.loan;

import cz.mendelu.ea.domain.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Min(0)
    private Double amount = 0.0;

    @NotNull
    @Min(0)
    private double interestRate = 0.0;

    @ManyToOne
    private Account attachedAccount;

    @NotNull
    private LocalDate startDate; // Changed from int to LocalDate

    @NotNull
    @Min(1)
    private int period;
}
