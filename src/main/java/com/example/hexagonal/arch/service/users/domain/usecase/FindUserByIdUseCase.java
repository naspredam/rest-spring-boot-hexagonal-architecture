package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

public interface FindUserByIdUseCase {

    ReactiveOptional<User> findById(UserId userId);
}
