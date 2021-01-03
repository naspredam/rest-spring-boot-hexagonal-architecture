package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.application.port.entrypoint.api.ChangeUserEndpointPort;
import com.example.service.user.application.usecase.ChangeExistingUserUseCase;
import com.example.service.user.application.usecase.DeleteUsersByIdUseCase;
import com.example.service.user.application.usecase.SubmitNewUserUseCase;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;

@Adapter
class ChangeUserEndpointAdapter implements ChangeUserEndpointPort {

    private final SubmitNewUserUseCase submitNewUserUseCase;

    private final ChangeExistingUserUseCase changeExistingUserUseCase;

    private final DeleteUsersByIdUseCase deleteUsersByIdUseCase;

    private final UserDtoMapper userDtoMapper;

    ChangeUserEndpointAdapter(SubmitNewUserUseCase submitNewUserUseCase,
                              ChangeExistingUserUseCase changeExistingUserUseCase,
                              DeleteUsersByIdUseCase deleteUsersByIdUseCase,
                              UserDtoMapper userDtoMapper) {
        this.submitNewUserUseCase = submitNewUserUseCase;
        this.changeExistingUserUseCase = changeExistingUserUseCase;
        this.deleteUsersByIdUseCase = deleteUsersByIdUseCase;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto saveUser(SaveUserBodyDto saveUserBodyDto) {
        User user = userDtoMapper.toDomainFromSaveBody(saveUserBodyDto);
        User savedUser = submitNewUserUseCase.saveUser(user);
        return userDtoMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(Integer id, SaveUserBodyDto saveUserBodyDto) {
        User user = userDtoMapper.toDomainFromSaveBody(id, saveUserBodyDto);
        User updatedUser = changeExistingUserUseCase.updateUser(user);
        return userDtoMapper.toDto(updatedUser);
    }


    @Override
    public void deleteUser(Integer id) {
        UserId userId = UserId.of(id);
        deleteUsersByIdUseCase.deleteById(userId);
    }

}
