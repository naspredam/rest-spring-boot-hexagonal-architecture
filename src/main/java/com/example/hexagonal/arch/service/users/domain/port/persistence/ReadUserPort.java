package com.example.hexagonal.arch.service.users.domain.port.persistence;

import com.example.hexagonal.arch.service.common.reactive.ReactiveCollection;
import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

public interface ReadUserPort {

    ReactiveOptional<Boolean> existsUserByName(User user);

    ReactiveOptional<Boolean> existsUserById(UserId userId);

    ReactiveOptional<User> fetchById(UserId userId);

    ReactiveCollection<User> fetchAll();
}
