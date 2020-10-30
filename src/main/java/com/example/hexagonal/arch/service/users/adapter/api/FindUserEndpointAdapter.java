package com.example.hexagonal.arch.service.users.adapter.api;

import com.example.hexagonal.arch.service.common.annotation.Adapter;
import com.example.hexagonal.arch.service.users.adapter.api.model.UserDto;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.api.FindUserEndpointPort;
import com.example.hexagonal.arch.service.users.domain.usecase.FindUserByIdUseCase;
import com.example.hexagonal.arch.service.users.domain.usecase.FindAllUsersUseCase;

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
