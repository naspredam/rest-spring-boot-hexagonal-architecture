package com.example.hexagonal.arch.service.users.adapter.api.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private final String firstName;

    private final String lastName;

    private final String phone;

}
