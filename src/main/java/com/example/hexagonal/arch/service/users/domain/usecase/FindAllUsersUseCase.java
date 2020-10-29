package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.users.domain.model.User;

import java.util.Collection;

public interface FindAllUsersUseCase {

    Collection<User> retrieveAllPersisted();
}
