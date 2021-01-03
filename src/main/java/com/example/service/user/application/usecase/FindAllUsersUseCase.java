package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;

import java.util.Collection;

public interface FindAllUsersUseCase {

    Collection<User> fetchAllPersisted();
}
