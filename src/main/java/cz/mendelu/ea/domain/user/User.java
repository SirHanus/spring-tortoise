package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    String name;

    @NotEmpty
    @Length(min = 3)
    String username;

    @NotNull
    @ManyToMany
    List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    List<Account> ownedAccounts = new ArrayList<>();

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public void attachAccount(Account account) {
        this.accounts.add(account);
        account.getUsers().add(this);
    }

}
