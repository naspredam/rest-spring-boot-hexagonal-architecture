package com.example.hexagonal.arch.service.users.adapter.api;

import com.example.hexagonal.arch.service.common.annotation.Adapter;
import com.example.hexagonal.arch.service.users.adapter.api.model.SaveUserBodyDto;
import com.example.hexagonal.arch.service.users.adapter.api.model.UserDto;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.api.ChangeUserEndpointPort;
import com.example.hexagonal.arch.service.users.domain.usecase.DeleteUsersByIdUseCase;
import com.example.hexagonal.arch.service.users.domain.usecase.SubmitNewUserUseCase;

@Adapter
class ChangeUserEndpointAdapter implements ChangeUserEndpointPort {

    private final SubmitNewUserUseCase submitNewUserUseCase;

    private final DeleteUsersByIdUseCase deleteUsersByIdUseCase;

    ChangeUserEndpointAdapter(SubmitNewUserUseCase submitNewUserUseCase,
                                     DeleteUsersByIdUseCase deleteUsersByIdUseCase) {
        this.submitNewUserUseCase = submitNewUserUseCase;
        this.deleteUsersByIdUseCase = deleteUsersByIdUseCase;
    }

    @Override
    public UserDto saveUser(SaveUserBodyDto saveUserBodyDto) {
        User user = UserDtoMapper.toDomainFromSaveBody(saveUserBodyDto);
        User userPersisted = submitNewUserUseCase.saveUser(user);
        return UserDtoMapper.toDto(userPersisted);
    }

    @Override
    public void deleteUser(Integer id) {
        UserId userId = UserId.of(id);
        deleteUsersByIdUseCase.deleteById(userId);
    }

}
