package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Length(min = 3)
    private String username;

    @ManyToMany
    @JoinTable(
            name = "user_accounts", // Name of the junction table
            joinColumns = @JoinColumn(name = "user_id"), // Column in the junction table referencing the current entity (User)
            inverseJoinColumns = @JoinColumn(name = "account_id") // Column in the junction table referencing the other entity (Account)
    )
    @NotNull
    private List<Account> accounts = new ArrayList<>();

    public void attachAccount(Account account) {
        this.accounts.add(account);
    }

}
