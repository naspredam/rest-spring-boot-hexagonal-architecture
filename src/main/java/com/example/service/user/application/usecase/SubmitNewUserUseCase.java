package com.example.service.user.application.usecase;

import com.example.service.user.domain.User;

public interface SubmitNewUserUseCase {

    User saveUser(User user);
}
