package com.example.service.user.domain.usecase;

import com.example.service.user.domain.model.User;

import java.util.Collection;

public interface FindAllUsersUseCase {

    Collection<User> retrieveAllPersisted();
}
