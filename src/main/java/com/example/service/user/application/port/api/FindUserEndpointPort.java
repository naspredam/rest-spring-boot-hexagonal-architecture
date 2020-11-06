package com.example.service.user.application.port.api;

import com.example.service.user.adapter.inbound.api.model.UserDto;

import java.util.Collection;

public interface FindUserEndpointPort {

    Collection<UserDto> fetchAllUsers();

    UserDto fetchUserById(Integer userId);
}
