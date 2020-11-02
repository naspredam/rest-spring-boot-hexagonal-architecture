package com.example.service.user.adapter.api;

import com.example.service.common.annotation.Mapper;
import com.example.service.user.adapter.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.api.model.UserDto;
import com.example.service.user.domain.model.FullName;
import com.example.service.user.domain.model.Phone;
import com.example.service.user.domain.model.User;

import static com.example.service.user.domain.model.UserFunctions.userFirstName;
import static com.example.service.user.domain.model.UserFunctions.userLastName;
import static com.example.service.user.domain.model.UserFunctions.userPhoneNumber;

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
