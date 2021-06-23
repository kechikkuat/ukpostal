package com.geopostal.ukpostal.services.users;

import com.geopostal.ukpostal.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public Optional<User> find(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users
                .values()
                .stream()
                .filter(u -> Objects.equals(username, u.getUsername()))
                .findFirst();
    }
}
