package com.example.hexagonal.arch.service.users.domain.port.persistence;

import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

import java.util.Collection;
import java.util.Optional;

public interface ReadUserPort {

    boolean existsUserByName(User user);

    boolean existsUserById(UserId userId);

    Optional<User> fetchById(UserId userId);

    Collection<User> fetchAll();
}
