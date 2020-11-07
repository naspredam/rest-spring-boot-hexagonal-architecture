package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;
import com.example.service.user.infrastructure.reactive.CollectionReactive;

public interface FindAllUsersUseCase {

    CollectionReactive<User> fetchAllPersisted();
}
