package com.example.hexagonal.arch.service.users.domain.usecase;

import com.example.hexagonal.arch.service.users.domain.model.UserId;

public interface DeleteUsersByIdUseCase {

    void deleteById(UserId userId);

}
