package ru.otus.web.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import ru.otus.web.model.User;

public class InMemoryUserDao implements UserDao {
    public static final String DEFAULT_USER = "admin";
    public static final String DEFAULT_PASSWORD = "1";
    private final Random random = new Random();
    private final Map<String, User> users;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put(DEFAULT_USER, new User(1L, DEFAULT_USER, DEFAULT_USER, DEFAULT_PASSWORD));
    }

    @Override
    public Optional<User> findRandomUser() {

        return users.values().stream().skip(random.nextInt(users.size() - 1)).findFirst();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(users.get(login));
    }
}
