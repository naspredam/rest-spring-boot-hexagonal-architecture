package com.example.service.user.application.port.api;

import com.example.service.user.adapter.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.api.model.UserDto;

public interface ChangeUserEndpointPort {

    UserDto saveUser(SaveUserBodyDto saveUserBodyDto);

    UserDto updateUser(Integer id, SaveUserBodyDto saveUserBodyDto);

    void deleteUser(Integer userId);

}
