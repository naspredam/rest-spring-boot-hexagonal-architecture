package com.example.hexagonal.arch.service.users.domain.port.api;

import com.example.hexagonal.arch.service.common.reactive.ReactiveCollection;
import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.adapter.api.model.SaveUserBodyDto;
import com.example.hexagonal.arch.service.users.adapter.api.model.UserDto;

public interface FindUserEndpointPort {

    ReactiveCollection<UserDto> fetchAllUsers();

    ReactiveOptional<UserDto> fetchUserById(Integer userId);
}
