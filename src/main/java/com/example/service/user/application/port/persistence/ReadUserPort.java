package com.example.service.user.application.port.persistence;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface ReadUserPort {

    Boolean existsUserByName(User user);

    Boolean existsUserById(UserId userId);

    Optional<User> fetchById(UserId userId);

    List<User> fetchAll();
}
