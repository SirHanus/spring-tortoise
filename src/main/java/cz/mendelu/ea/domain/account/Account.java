package cz.mendelu.ea.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.mendelu.ea.domain.transaction.Transaction;
import cz.mendelu.ea.domain.user.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Account {

    @NotNull
    private Long id;

    @NotNull
    private User owner;

    @NotNull
    @Min(0)
    private double balance;

    @NotNull
    @JsonIgnore
    private List<Transaction> transactions;

    @NotNull
    @JsonIgnore
    private List<User> users;

    public Account() {
        this.id = -1L;
        this.owner = null;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Account(Long id, double balance) {
        this.id = id;
        this.owner = null;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Account(Long id, User user, double balance) {
        this.id = id;
        this.owner = user;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.users = new ArrayList<>();
    }


    public void addUser(User user) {
        this.users.add(user);
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
