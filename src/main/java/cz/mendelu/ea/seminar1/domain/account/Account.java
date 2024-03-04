package cz.mendelu.ea.seminar1.domain.account;

import cz.mendelu.ea.seminar1.domain.transaction.Transaction;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Account {

    @NonNull
    @NotNull
    private Long id;

    @NonNull
    @NotEmpty
    private String owner;

    @NonNull
    @NotNull
    @Min((0))
    private double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "source_account_id")
    private List<Transaction> transactions = new ArrayList<>();

    public Account(@NonNull Long id, @NonNull String owner, @NonNull double balance, Transaction transaction) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
        this.transactions.add(transaction);
    }

    public void updateAccount(Account newAccountDetails, Transaction transaction) {
        this.setOwner(newAccountDetails.getOwner());
        this.setBalance(newAccountDetails.getBalance());
        this.getTransactions().add(transaction);
    }
    public void updateAccount(Account newAccountDetails) {
        this.setOwner(newAccountDetails.getOwner());
        this.setBalance(newAccountDetails.getBalance());
    }
}
