package com.example.service.user.application.service;

import com.example.service.user.application.port.outbound.persistence.ReadUserPort;
import com.example.service.user.application.port.outbound.persistence.WriteUserPort;
import com.example.service.user.application.usecase.DeleteUsersByIdUseCase;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.SingleReactive;
import com.example.service.user.infrastructure.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
class DeleteUsersByIdService implements DeleteUsersByIdUseCase {

    private final WriteUserPort writeUserPort;

    private final ReadUserPort readUserPort;

    DeleteUsersByIdService(WriteUserPort writeUserPort,
                           ReadUserPort readUserPort) {
        this.writeUserPort = writeUserPort;
        this.readUserPort = readUserPort;
    }

    @Override
    public SingleReactive<Void> deleteById(UserId userId) {
        ObjectValidator.validate(userId);

        return readUserPort.existsUserById(userId)
                .flatMap(userExists -> userExists ?
                        writeUserPort.deleteById(userId) :
                        SingleReactive.error(new IllegalArgumentException("User missed on the repository, not able to delete it...")));
    }
}
