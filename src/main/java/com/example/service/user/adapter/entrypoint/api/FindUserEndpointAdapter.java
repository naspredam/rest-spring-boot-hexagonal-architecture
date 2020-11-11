package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.application.port.entrypoint.api.FindUserEndpointPort;
import com.example.service.user.application.usecase.FindAllUsersUseCase;
import com.example.service.user.application.usecase.FindUserByIdUseCase;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.UnitReactive;

@Adapter
class FindUserEndpointAdapter implements FindUserEndpointPort {

    private final FindAllUsersUseCase findAllUsersUseCase;

    private final FindUserByIdUseCase findUserByIdUseCase;

    private final UserDtoMapper userDtoMapper;

    FindUserEndpointAdapter(FindAllUsersUseCase findAllUsersUseCase,
                            FindUserByIdUseCase findUserByIdUseCase,
                            UserDtoMapper userDtoMapper) {
        this.findAllUsersUseCase = findAllUsersUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UnitReactive<UserDto> fetchUserById(Integer id) {
        UserId userId = UserId.of(id);
        return findUserByIdUseCase.findById(userId)
                .map(userDtoMapper::toDto);
    }

    @Override
    public CollectionReactive<UserDto> fetchAllUsers() {
        return findAllUsersUseCase.fetchAllPersisted()
                .map(userDtoMapper::toDto);
    }
}
