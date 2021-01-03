package com.example.service.user.application.port.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.UserDto;

import java.util.Collection;

public interface FindUserEndpointPort {

    Collection<UserDto> fetchAllUsers();

    UserDto fetchUserById(Integer userId);
}
