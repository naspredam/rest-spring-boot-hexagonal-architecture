package com.example.service.user.adapter.api;

import com.example.service.common.annotation.Adapter;
import com.example.service.user.adapter.api.model.UserDto;
import com.example.service.user.application.port.api.FindUserEndpointPort;
import com.example.service.user.domain.model.UserId;
import com.example.service.user.domain.usecase.FindAllUsersUseCase;
import com.example.service.user.domain.usecase.FindUserByIdUseCase;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Adapter
class FindUserEndpointAdapter implements FindUserEndpointPort {

    private final FindAllUsersUseCase findAllUsersUseCase;

    private final FindUserByIdUseCase findUserByIdUseCase;

    FindUserEndpointAdapter(FindAllUsersUseCase findAllUsersUseCase,
                            FindUserByIdUseCase findUserByIdUseCase) {
        this.findAllUsersUseCase = findAllUsersUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @Override
    public Collection<UserDto> fetchAllUsers() {
        return findAllUsersUseCase.retrieveAllPersisted().stream()
                .map(UserDtoMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<UserDto> fetchUserById(Integer id) {
        UserId userId = UserId.of(id);
        return findUserByIdUseCase.findById(userId)
                .map(UserDtoMapper::toDto);
    }

}
