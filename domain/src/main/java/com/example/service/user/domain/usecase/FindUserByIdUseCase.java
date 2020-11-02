package com.example.service.user.domain.usecase;

import com.example.service.user.domain.model.User;
import com.example.service.user.domain.model.UserId;

import java.util.Optional;

public interface FindUserByIdUseCase {

    Optional<User> findById(UserId userId);
}
