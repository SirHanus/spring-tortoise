package cz.mendelu.ea.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.mendelu.ea.domain.account.Account;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    @Min(0)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String username;
//
//    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinTable(
//            name = "user_account",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "account_id")
//    )


    @NotNull
    @JsonIgnore
    private List<Account> accounts;

    public User(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.accounts = new ArrayList<>();
    }

    public User(){
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }
}
