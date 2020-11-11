package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.application.usecase.FindAllUsersUseCase;
import com.example.service.user.application.usecase.FindUserByIdUseCase;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.UnitReactive;
import com.example.service.user.infrastructure.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
class FindUserService implements FindUserByIdUseCase, FindAllUsersUseCase {

    private final ReadUserPort readUserPort;

    FindUserService(ReadUserPort readUserPort) {
        this.readUserPort = readUserPort;
    }

    @Override
    public UnitReactive<User> findById(UserId userId) {
        ObjectValidator.validate(userId);
        return readUserPort.fetchById(userId);
    }

    @Override
    public CollectionReactive<User> fetchAllPersisted() {
        return readUserPort.fetchAll();
    }

}
