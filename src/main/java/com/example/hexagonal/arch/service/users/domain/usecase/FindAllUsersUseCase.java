package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.common.reactive.ReactiveCollection;
import com.example.hexagonal.arch.service.users.domain.model.User;

public interface FindAllUsersUseCase {

    ReactiveCollection<User> retrieveAllPersisted();
}
