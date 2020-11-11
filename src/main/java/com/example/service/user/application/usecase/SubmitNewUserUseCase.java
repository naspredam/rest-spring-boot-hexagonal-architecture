package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;
import com.example.service.user.infrastructure.reactive.UnitReactive;

public interface SubmitNewUserUseCase {

    UnitReactive<User> saveUser(User user);
}
