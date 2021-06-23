package com.geopostal.ukpostal.services.users;

import com.geopostal.ukpostal.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);
}
