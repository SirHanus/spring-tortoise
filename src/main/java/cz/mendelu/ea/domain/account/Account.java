package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.transaction.Transaction;
import cz.mendelu.ea.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    @Transient
    private User owner = new User(1L, "John", "Doe", List.of());

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
    @Transient
    private List<User> users = new ArrayList<>();

    public Account(Long id, User owner, double balance) {
        this.id = id;
        this.balance = balance;
        setOwner(owner);
    }

    public int getTransactionCount() {
        return outgoingTransactions.size()+incomingTransactions.size();
    }

    public void attachUser(User user) {
        this.users.add(user);
    }

    public void setOwner(User owner) {
        attachUser(owner);
        this.owner = owner;
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
