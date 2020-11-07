package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.SingleReactive;

import java.util.Optional;

public interface FindUserByIdUseCase {

    SingleReactive<User> findById(UserId userId);
}
