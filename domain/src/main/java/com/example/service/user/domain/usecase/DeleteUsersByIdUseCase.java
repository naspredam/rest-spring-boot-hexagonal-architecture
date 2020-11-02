package com.example.service.user.domain.usecase;

import com.example.service.user.domain.model.UserId;

public interface DeleteUsersByIdUseCase {

    void deleteById(UserId userId);

}
