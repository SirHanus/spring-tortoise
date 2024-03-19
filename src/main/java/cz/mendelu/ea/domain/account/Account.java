package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.transaction.Transaction;
import cz.mendelu.ea.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User owner;

    @NotEmpty
    private String name;

    @NotNull
    @Min(0)
    private double balance;

    @NotNull
    @OneToMany(mappedBy = "sourceAccount")
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @NotNull
    @OneToMany(mappedBy = "targetAccount")
    private List<Transaction> incomingTransactions = new ArrayList<>();

    @NotNull
    @ManyToMany(mappedBy = "accounts")
    private List<User> users = new ArrayList<>();

    public Account(User owner, String name, double balance) {
        this.name = name;
        this.balance = balance;
        setOwner(owner);
    }

    public int getTransactionCount() {
        return incomingTransactions.size() + outgoingTransactions.size();
    }

    public void setOwner(User owner) {
        owner.attachAccount(this);
        this.owner = owner;
        owner.getOwnedAccounts().add(this);
    }

    public void processTransaction(Transaction transaction) {
        if (transaction.getSourceAccount().getId().equals(id)) {
            balance -= transaction.getAmount();
            outgoingTransactions.add(transaction);
        } else if (transaction.getTargetAccount().getId().equals(id)) {
            balance += transaction.getAmount();
            incomingTransactions.add(transaction);
        }
    }

}
