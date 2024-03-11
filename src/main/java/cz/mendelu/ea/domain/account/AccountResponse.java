package cz.mendelu.ea.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.mendelu.ea.domain.transaction.Transaction;
import cz.mendelu.ea.domain.user.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AccountResponse {

    @NotNull
    private Long id;

    @NotNull
    private Long owner;

    @NotNull
    @Min(0)
    private double balance;

    @NotNull
    @JsonIgnore
    private List<Transaction> transactions;

    @NotNull
    @JsonIgnore
    private List<Long> users;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.owner = account.getOwner().getId();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions();
        this.users = account.getUsers().stream().map(User::getId).toList();
    }
}
