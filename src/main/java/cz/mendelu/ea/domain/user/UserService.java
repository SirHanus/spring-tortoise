package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add); // must convert iterable to list
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    public void deleteUser(Long id) {
        User user = new User();
        if (repository.findById(id).isPresent()) {
            user = repository.findById(id).get();
            for (Account account : user.getAccounts()) {
                account.getUsers().remove(user);
            }
        }


        repository.deleteById(id);
    }

}
