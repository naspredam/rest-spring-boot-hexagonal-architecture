package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.domain.FullName;
import com.example.service.user.domain.Phone;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Mapper;

import static com.example.service.user.domain.UserFunctions.userFirstName;
import static com.example.service.user.domain.UserFunctions.userLastName;
import static com.example.service.user.domain.UserFunctions.userPhoneNumber;

@Mapper
class UserDtoMapper {

    UserDtoMapper() {
        super();
    }

    UserDto toDto(User user) {
        return UserDto.builder()
                .firstName(userFirstName.apply(user))
                .lastName(userLastName.apply(user))
                .phone(userPhoneNumber.apply(user))
                .build();
    }

    User toDomainFromSaveBody(SaveUserBodyDto saveUserBodyDto) {
        return User.builder()
                .fullName(FullName.of(saveUserBodyDto.getFirstName(), null, saveUserBodyDto.getLastName()))
                .phone(Phone.of(saveUserBodyDto.getPhone()))
                .build();
    }

    User toDomainFromSaveBody(Integer userId, SaveUserBodyDto saveUserBodyDto) {
        return User.builder()
                .id(UserId.of(userId))
                .fullName(FullName.of(saveUserBodyDto.getFirstName(), null, saveUserBodyDto.getLastName()))
                .phone(Phone.of(saveUserBodyDto.getPhone()))
                .build();
    }
}
