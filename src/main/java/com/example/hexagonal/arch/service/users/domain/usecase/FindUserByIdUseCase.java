package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

import java.util.Optional;

public interface FindUserByIdUseCase {

    Optional<User> findById(UserId userId);
}
