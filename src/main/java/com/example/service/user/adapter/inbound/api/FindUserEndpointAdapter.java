package com.example.service.user.adapter.inbound.api;

import com.example.service.user.adapter.inbound.api.model.UserDto;
import com.example.service.user.application.port.api.FindUserEndpointPort;
import com.example.service.user.application.usecase.FindAllUsersUseCase;
import com.example.service.user.application.usecase.FindUserByIdUseCase;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;

import java.util.Collection;
import java.util.stream.Collectors;

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
    public UserDto fetchUserById(Integer id) {
        UserId userId = UserId.of(id);
        User user = findUserByIdUseCase.findById(userId);
        return userDtoMapper.toDto(user);
    }

    @Override
    public Collection<UserDto> fetchAllUsers() {
        return findAllUsersUseCase.fetchAllPersisted()
                .stream()
                .map(userDtoMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
