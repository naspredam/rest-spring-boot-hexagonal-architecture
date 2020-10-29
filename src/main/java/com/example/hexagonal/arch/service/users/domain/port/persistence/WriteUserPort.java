package com.example.hexagonal.arch.service.users.domain.port.persistence;

import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

public interface WriteUserPort {

    ReactiveOptional<User> saveNew(User user);

    ReactiveOptional<Void> deleteById(UserId userId);
}
