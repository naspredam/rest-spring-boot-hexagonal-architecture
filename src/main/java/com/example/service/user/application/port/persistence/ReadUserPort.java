package com.example.service.user.application.port.persistence;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.SingleReactive;

import java.util.Collection;
import java.util.Optional;

public interface ReadUserPort {

    SingleReactive<Boolean> existsUserByName(User user);

    SingleReactive<Boolean> existsUserById(UserId userId);

    SingleReactive<User> fetchById(UserId userId);

    CollectionReactive<User> fetchAll();
}
