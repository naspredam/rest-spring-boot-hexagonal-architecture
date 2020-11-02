package com.example.service.user.application.port.persistence;

import com.example.service.user.domain.model.User;
import com.example.service.user.domain.model.UserId;

import java.util.Collection;
import java.util.Optional;

public interface ReadUserPort {

    boolean existsUserByName(User user);

    boolean existsUserById(UserId userId);

    Optional<User> fetchById(UserId userId);

    Collection<User> fetchAll();
}
