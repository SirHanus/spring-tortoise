package cz.mendelu.ea.domain.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User createUser(User user) {
        Long maxId = users.stream()
                .map(User::getId)
                .max(Long::compareTo)
                .orElse(0L);
        user.setId(maxId + 1);
        users.add(user);
        return user;
    }

}
