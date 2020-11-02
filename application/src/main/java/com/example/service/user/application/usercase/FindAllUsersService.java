package com.example.service.user.application.usercase;

import com.example.service.common.annotation.UseCase;
import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.domain.model.User;
import com.example.service.user.domain.usecase.FindAllUsersUseCase;

import java.util.Collection;

@UseCase
class FindAllUsersService implements FindAllUsersUseCase {

    private final ReadUserPort readUserPort;

    FindAllUsersService(ReadUserPort readUserPort) {
        this.readUserPort = readUserPort;
    }

    @Override
    public Collection<User> retrieveAllPersisted() {
        return readUserPort.fetchAll();
    }
}
