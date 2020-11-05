package com.example.service.user.application.port.api;

import com.example.service.user.adapter.api.model.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface FindUserEndpointPort {

    Collection<UserDto> fetchAllUsers();

    Optional<UserDto> fetchUserById(Integer userId);
}
