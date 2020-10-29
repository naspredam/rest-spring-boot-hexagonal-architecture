package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.domain.model.User;

public interface SubmitNewUserUseCase {

    ReactiveOptional<User> saveUser(User user);
}
