package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    private double amount;

    @NotNull
    @ManyToOne
    private Account sourceAccount;

    @NotNull
    @ManyToOne
    private Account targetAccount;

}
