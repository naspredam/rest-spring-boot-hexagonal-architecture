package com.example.service.user.application.port.persistence;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;

import java.util.Optional;

public interface WriteUserPort {

    User saveNew(User user);

    Optional<User> update(User user);

    void deleteById(UserId userId);
}
