package ru.otus.web.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import ru.otus.web.model.User;

public class InMemoryUserDao implements UserDao {

    public static final String DEFAULT_PASSWORD = "1";
    private final Random random = new Random();
    private final Map<Long, User> users;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put(1L, new User(1L, "admin", "admin", DEFAULT_PASSWORD));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findRandomUser() {

        return users.values().stream().skip(random.nextInt(users.size() - 1)).findFirst();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.values().stream().filter(v -> v.login().equals(login)).findFirst();
    }
}
