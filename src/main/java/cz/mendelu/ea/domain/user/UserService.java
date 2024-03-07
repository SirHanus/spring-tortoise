package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users;

    @Autowired
    public UserService(AccountService accountService) {
        this.users = new ArrayList<>();

        User user1 = new User(1L, "Patrick", "patric2023");
        this.users.add(user1);

        User user2 = new User(2L, "Samantha", "sammyBee");
        this.users.add(user2);

        User user3 = new User(3L, "Alexander", "alexTheGreat");
        this.users.add(user3);

        User user4 = new User(4L, "Isabella", "bellaStar");
        this.users.add(user4);

        User user5 = new User(5L, "Michael", "mike123");
        this.users.add(user5);

        User user6 = new User(6L, "Jessica", "jessieWaves");
        this.users.add(user6);

        User user7 = new User(7L, "Daniel", "danTheMan");
        this.users.add(user7);

        User user8 = new User(8L, "Emily", "emmy2023");
        this.users.add(user8);

        User user9 = new User(9L, "James", "jimmyCricket");
        this.users.add(user9);

        User user10 = new User(10L, "Sophia", "sophieSun");
        this.users.add(user10);



        accountService.getAccount(1L).ifPresent(account ->{
            user1.addAccount(account);
            account.addUser(user1);
        });

    }

    public List<User> getAllUsers() {
        return this.users;
    }

    public Optional<User> getUser(Long id) {
        return this.getAllUsers().stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }
}
