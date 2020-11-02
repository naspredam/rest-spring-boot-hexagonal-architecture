package com.example.service.user.domain.usecase;

import com.example.service.user.domain.model.User;

public interface SubmitNewUserUseCase {

    User saveUser(User user);
}
