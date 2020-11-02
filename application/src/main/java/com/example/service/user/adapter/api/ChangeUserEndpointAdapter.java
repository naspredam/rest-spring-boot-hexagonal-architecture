package com.example.service.user.adapter.api;

import com.example.service.common.annotation.Adapter;
import com.example.service.user.adapter.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.api.model.UserDto;
import com.example.service.user.application.port.api.ChangeUserEndpointPort;
import com.example.service.user.domain.model.User;
import com.example.service.user.domain.model.UserId;
import com.example.service.user.domain.usecase.DeleteUsersByIdUseCase;
import com.example.service.user.domain.usecase.SubmitNewUserUseCase;

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
