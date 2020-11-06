package com.example.service.user.application.port.inbound.api;

import com.example.service.user.adapter.inbound.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.inbound.api.model.UserDto;

public interface ChangeUserEndpointPort {

    UserDto saveUser(SaveUserBodyDto saveUserBodyDto);

    UserDto updateUser(Integer id, SaveUserBodyDto saveUserBodyDto);

    void deleteUser(Integer userId);

}
