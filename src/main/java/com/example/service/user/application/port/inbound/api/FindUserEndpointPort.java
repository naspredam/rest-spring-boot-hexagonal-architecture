package com.example.service.user.application.port.inbound.api;

import com.example.service.user.adapter.inbound.api.model.UserDto;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.SingleReactive;

public interface FindUserEndpointPort {

    CollectionReactive<UserDto> fetchAllUsers();

    SingleReactive<UserDto> fetchUserById(Integer userId);
}
