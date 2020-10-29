package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.users.domain.model.User;

public interface SubmitNewUserUseCase {

    User saveUser(User user);
}
