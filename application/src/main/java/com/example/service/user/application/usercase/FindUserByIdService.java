package com.example.service.user.application.usercase;

import com.example.service.common.annotation.UseCase;
import com.example.service.common.validator.ObjectValidator;
import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.domain.model.User;
import com.example.service.user.domain.model.UserId;
import com.example.service.user.domain.usecase.FindUserByIdUseCase;

import java.util.Optional;

@UseCase
class FindUserByIdService implements FindUserByIdUseCase {

    private final ReadUserPort readUserPort;

    FindUserByIdService(ReadUserPort readUserPort) {
        this.readUserPort = readUserPort;
    }

    @Override
    public Optional<User> findById(UserId userId) {
        ObjectValidator.validate(userId);
        return readUserPort.fetchById(userId);
    }
}
