package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

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
    private List<Long> accountIDs;

    @NotEmpty
    private double average;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.accountIDs = user.getAccounts().stream().map(Account::getId).toList();
        this.average = user.AverageTransactions();
    }



}
