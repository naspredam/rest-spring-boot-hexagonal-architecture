package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

public interface DeleteUsersByIdUseCase {

    ReactiveOptional<Void> deleteById(UserId userId);

}
