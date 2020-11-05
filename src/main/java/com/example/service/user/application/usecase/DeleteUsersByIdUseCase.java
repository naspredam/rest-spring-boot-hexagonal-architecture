package com.example.service.user.application.usecase;

import com.example.service.user.domain.UserId;

public interface DeleteUsersByIdUseCase {

    void deleteById(UserId userId);

}
