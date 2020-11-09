package com.example.service.user.application.port.inbound.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.infrastructure.reactive.SingleReactive;

public interface ChangeUserEndpointPort {

    SingleReactive<UserDto> saveUser(SaveUserBodyDto saveUserBodyDto);

    SingleReactive<UserDto> updateUser(Integer id, SaveUserBodyDto saveUserBodyDto);

    SingleReactive<Void> deleteUser(Integer userId);

}
