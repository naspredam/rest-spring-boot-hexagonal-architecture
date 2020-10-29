package com.example.hexagonal.arch.service.users.domain.port.api;

import com.example.hexagonal.arch.service.users.adapter.api.model.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface FindUserEndpointPort {

    Collection<UserDto> fetchAllUsers();

    Optional<UserDto> fetchUserById(Integer userId);
}
