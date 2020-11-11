package com.example.service.user.application.port.persistence;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.UnitReactive;

public interface ReadUserPort {

    UnitReactive<Boolean> existsUserByName(User user);

    UnitReactive<Boolean> existsUserById(UserId userId);

    UnitReactive<User> fetchById(UserId userId);

    CollectionReactive<User> fetchAll();
}
