package com.example.hexagonal.arch.service.users.application;

import com.example.hexagonal.arch.service.common.annotation.UseCase;
import com.example.hexagonal.arch.service.common.validator.ObjectValidator;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.persistence.ReadUserPort;
import com.example.hexagonal.arch.service.users.domain.port.persistence.WriteUserPort;
import com.example.hexagonal.arch.service.users.domain.usecase.DeleteUsersByIdUseCase;

@UseCase
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

        if(readUserPort.existsUserById(userId)) {
            writeUserPort.deleteById(userId);
            return;
        }

        throw new IllegalAccessError("User missed on the repository, not able to delete it...");
    }
}
