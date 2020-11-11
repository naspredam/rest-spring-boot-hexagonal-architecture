package com.example.service.user.application.port.persistence;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.UnitReactive;

public interface WriteUserPort {

    UnitReactive<User> saveNew(User user);

    UnitReactive<User> update(User user);

    UnitReactive<Void> deleteById(UserId userId);
}
