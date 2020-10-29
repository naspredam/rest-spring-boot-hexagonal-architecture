package com.example.hexagonal.arch.service.users.application;

import com.example.hexagonal.arch.service.common.reactive.ReactiveCollection;
import com.example.hexagonal.arch.service.common.annotation.UseCase;
import com.example.hexagonal.arch.service.users.domain.port.persistence.ReadUserPort;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.usecase.FindAllUsersUseCase;

@UseCase
class FindAllUsersService implements FindAllUsersUseCase {

    private final ReadUserPort readUserPort;

    FindAllUsersService(ReadUserPort readUserPort) {
        this.readUserPort = readUserPort;
    }

    @Override
    public ReactiveCollection<User> retrieveAllPersisted() {
        return readUserPort.fetchAll();
    }
}
