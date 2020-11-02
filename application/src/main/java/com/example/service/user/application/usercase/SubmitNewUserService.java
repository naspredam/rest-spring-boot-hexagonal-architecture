package com.example.service.user.application.usercase;

import com.example.service.common.annotation.UseCase;
import com.example.service.common.validator.ObjectValidator;
import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.model.User;
import com.example.service.user.domain.usecase.SubmitNewUserUseCase;

@UseCase
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

        if (readUserPort.existsUserByName(user)) {
            throw new IllegalAccessError("User duplicated...");
        }

        return writeUserPort.saveNew(user);
    }
}
