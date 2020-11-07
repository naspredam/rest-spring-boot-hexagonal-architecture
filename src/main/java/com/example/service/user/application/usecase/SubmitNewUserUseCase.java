package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;
import com.example.service.user.infrastructure.reactive.SingleReactive;

public interface SubmitNewUserUseCase {

    SingleReactive<User> saveUser(User user);
}
