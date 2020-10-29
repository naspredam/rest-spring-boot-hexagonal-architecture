package com.example.hexagonal.arch.service.users.adapter.api;

import com.example.hexagonal.arch.service.users.adapter.api.model.SaveUserBodyDto;
import com.example.hexagonal.arch.service.users.adapter.api.model.UserDto;
import com.example.hexagonal.arch.service.common.annotation.Mapper;
import com.example.hexagonal.arch.service.users.domain.model.FullName;
import com.example.hexagonal.arch.service.users.domain.model.Phone;
import com.example.hexagonal.arch.service.users.domain.model.User;

import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userFirstName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userIdAsInt;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userLastName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userPhoneNumber;

@Mapper
class UserDtoMapper {

    private UserDtoMapper() {
        super();
    }

    static UserDto toDto(User user) {
        return UserDto.builder()
                .firstName(userFirstName.apply(user))
                .lastName(userLastName.apply(user))
                .phone(userPhoneNumber.apply(user))
                .build();
    }

    static User toDomainFromSaveBody(SaveUserBodyDto saveUserBodyDto) {
        return User.builder()
                .fullName(FullName.of(saveUserBodyDto.getFirstName(), null, saveUserBodyDto.getLastName()))
                .phone(Phone.of(saveUserBodyDto.getPhone()))
                .build();
    }
}
