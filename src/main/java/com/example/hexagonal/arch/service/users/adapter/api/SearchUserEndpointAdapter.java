package com.example.hexagonal.arch.service.users.adapter.api;

import com.example.hexagonal.arch.service.common.annotation.Adapter;
import com.example.hexagonal.arch.service.common.reactive.ReactiveCollection;
import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.adapter.api.model.UserDto;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.api.FindUserEndpointPort;
import com.example.hexagonal.arch.service.users.domain.usecase.FindUserByIdUseCase;
import com.example.hexagonal.arch.service.users.domain.usecase.FindAllUsersUseCase;

@Adapter
class SearchUserEndpointAdapter implements FindUserEndpointPort {

    private final FindAllUsersUseCase findAllUsersUseCase;

    private final FindUserByIdUseCase findUserByIdUseCase;

    SearchUserEndpointAdapter(FindAllUsersUseCase findAllUsersUseCase,
                                     FindUserByIdUseCase findUserByIdUseCase) {
        this.findAllUsersUseCase = findAllUsersUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @Override
    public ReactiveCollection<UserDto> fetchAllUsers() {
        return findAllUsersUseCase.retrieveAllPersisted()
                .map(UserDtoMapper::toDto);
    }

    @Override
    public ReactiveOptional<UserDto> fetchUserById(Integer id) {
        UserId userId = UserId.of(id);
        return findUserByIdUseCase.findById(userId)
                .map(UserDtoMapper::toDto);
    }

}
