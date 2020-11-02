package com.example.service.user.application.usercase;

import com.example.service.common.annotation.UseCase;
import com.example.service.common.validator.ObjectValidator;
import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.model.UserId;
import com.example.service.user.domain.usecase.DeleteUsersByIdUseCase;

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
