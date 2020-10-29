package com.example.hexagonal.arch.service.users.application;

import com.example.hexagonal.arch.service.common.annotation.UseCase;
import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.domain.usecase.SubmitNewUserUseCase;
import com.example.hexagonal.arch.service.common.validator.ObjectValidator;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.port.persistence.ReadUserPort;
import com.example.hexagonal.arch.service.users.domain.port.persistence.WriteUserPort;

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
    public ReactiveOptional<User> saveUser(User user) {
        ObjectValidator.validate(user);

        return readUserPort.existsUserByName(user)
                .flatMap(userExists -> userExists ?
                        ReactiveOptional.error(new IllegalAccessError("User duplicated...")) :
                        writeUserPort.saveNew(user));
    }
}
