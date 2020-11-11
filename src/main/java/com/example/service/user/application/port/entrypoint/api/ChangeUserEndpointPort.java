package com.example.service.user.application.port.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.infrastructure.reactive.UnitReactive;

public interface ChangeUserEndpointPort {

    UnitReactive<UserDto> saveUser(SaveUserBodyDto saveUserBodyDto);

    UnitReactive<UserDto> updateUser(Integer id, SaveUserBodyDto saveUserBodyDto);

    UnitReactive<Void> deleteUser(Integer userId);

}
