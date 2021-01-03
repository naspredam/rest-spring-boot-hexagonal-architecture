package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.application.usecase.FindAllUsersUseCase;
import com.example.service.user.application.usecase.FindUserByIdUseCase;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.validator.ObjectValidator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
class FindUserService implements FindUserByIdUseCase, FindAllUsersUseCase {

    private final ReadUserPort readUserPort;

    FindUserService(ReadUserPort readUserPort) {
        this.readUserPort = readUserPort;
    }

    @Override
    public User findById(UserId userId) {
        ObjectValidator.validate(userId);
        return readUserPort.fetchById(userId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Collection<User> fetchAllPersisted() {
        return readUserPort.fetchAll();
    }

}
