package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.transaction.Transaction;
import cz.mendelu.ea.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.Set;

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
    @EqualsAndHashCode.Exclude
    private Set<Transaction> outgoingTransactions = new HashSet<>();

    @NotNull
    @OneToMany(mappedBy = "targetAccount")
    @EqualsAndHashCode.Exclude
    private Set<Transaction> incomingTransactions = new HashSet<>();

    @NotNull
    @ManyToMany(mappedBy = "accounts")
    @EqualsAndHashCode.Exclude
    private Set<User> users = new HashSet<>();

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

    @PreRemove
    public void detachAccountFromTransactions() {
        for (Transaction transaction : incomingTransactions) {
            transaction.setTargetAccount(null);
        }
        for (Transaction transaction : outgoingTransactions) {
            transaction.setSourceAccount(null);
        }
    }

    public double getSumOfAllTransactions() {
        double sum = 0;

        for (Transaction transaction : incomingTransactions) {
            if (transaction.getAmount() < 0) {
                throw new DataIntegrityViolationException("Transaction amount must be positive.");
            }
            sum += transaction.getAmount();
        }

        for (Transaction transaction : outgoingTransactions) {
            if (transaction.getAmount() < 0) {
                throw new DataIntegrityViolationException("Transaction amount must be positive.");
            }
            sum -= transaction.getAmount();
        }

        return sum;
    }

}
