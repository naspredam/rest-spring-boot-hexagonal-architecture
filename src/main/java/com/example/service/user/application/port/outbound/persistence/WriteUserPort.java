package com.example.service.user.application.port.outbound.persistence;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.SingleReactive;

import java.util.Optional;

public interface WriteUserPort {

    SingleReactive<User> saveNew(User user);

    SingleReactive<User> update(User user);

    SingleReactive<Void> deleteById(UserId userId);
}
