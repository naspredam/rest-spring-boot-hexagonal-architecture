package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.application.usecase.SubmitNewUserUseCase;
import com.example.service.user.domain.User;
import com.example.service.user.infrastructure.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
class SubmitNewUserService implements SubmitNewUserUseCase {

    private final WriteUserPort writeUserPort;

    private final ReadUserPort readUserPort;

    SubmitNewUserService(WriteUserPort writeUserPort,
                         ReadUserPort readUserPort) {
        this.writeUserPort = writeUserPort;
        this.readUserPort = readUserPort;
    }

    @Override
    public User saveUser(User user) {
        ObjectValidator.validate(user);

        if(readUserPort.existsUserByName(user)) {
            throw new IllegalArgumentException("User duplicated...");
        }
        return writeUserPort.saveNew(user);
    }
}
