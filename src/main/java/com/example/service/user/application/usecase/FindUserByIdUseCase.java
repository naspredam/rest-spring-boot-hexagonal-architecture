package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;

import java.util.Optional;

public interface FindUserByIdUseCase {

    User findById(UserId userId);
}
