package com.example.service.user.adapter.entrypoint.api.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private final String firstName;

    private final String lastName;

    private final String phone;

}
