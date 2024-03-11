package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class UserRequest {

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


    public UserRequest(String name, String username) {
        this.name = name;
        this.username = username;
        this.accountIDs = new ArrayList<>();
    }

    @NotNull
    private List<Long> accountIDs;

    public void toUser(User user, AccountService accountService) {
        user.setName(this.name);
        user.setUsername(this.username);

        List<Account> accounts = accountIDs.stream()
                .map(accountService::getAccount)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        user.setAccounts(accounts);
        accounts.forEach(a -> a.addUser(user));

    }

}
