package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.exceptions.DuplicateObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();



    public void addUser(User user){
        if (users.stream().map(User::getId).toList().contains(user.getId())){
            throw new DuplicateObjectException();
        }

        user.setId((long) users.size());
        this.users.add(user);
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
