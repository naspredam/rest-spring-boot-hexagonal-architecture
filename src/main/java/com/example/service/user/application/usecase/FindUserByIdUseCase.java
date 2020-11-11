package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.UnitReactive;

public interface FindUserByIdUseCase {

    UnitReactive<User> findById(UserId userId);
}
