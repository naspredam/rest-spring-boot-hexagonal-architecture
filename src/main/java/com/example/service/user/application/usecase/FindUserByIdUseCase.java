package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;

public interface FindUserByIdUseCase {

    User findById(UserId userId);
}
