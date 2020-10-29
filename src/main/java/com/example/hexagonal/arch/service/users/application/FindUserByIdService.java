package com.example.hexagonal.arch.service.users.application;

import com.example.hexagonal.arch.service.common.annotation.UseCase;
import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.common.validator.ObjectValidator;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.persistence.ReadUserPort;
import com.example.hexagonal.arch.service.users.domain.usecase.FindUserByIdUseCase;

@UseCase
class FindUserByIdService implements FindUserByIdUseCase {

    private final ReadUserPort readUserPort;

    FindUserByIdService(ReadUserPort readUserPort) {
        this.readUserPort = readUserPort;
    }

    @Override
    public ReactiveOptional<User> findById(UserId userId) {
        ObjectValidator.validate(userId);
        return readUserPort.fetchById(userId);
    }
}
