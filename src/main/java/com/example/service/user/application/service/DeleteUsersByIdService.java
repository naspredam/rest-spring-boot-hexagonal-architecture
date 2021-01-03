package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.application.usecase.DeleteUsersByIdUseCase;
import com.example.service.user.domain.UserId;
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
    public void deleteById(UserId userId) {
        ObjectValidator.validate(userId);

        if (!readUserPort.existsUserById(userId)) {
            throw new IllegalArgumentException("User missed on the repository, not able to delete it...");
        }

        writeUserPort.deleteById(userId);
    }
}
