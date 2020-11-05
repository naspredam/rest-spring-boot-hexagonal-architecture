package com.example.service.user.adapter.api;

import com.example.service.user.adapter.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.api.model.UserDto;
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

    static User toDomainFromSaveBody(Integer userId, SaveUserBodyDto saveUserBodyDto) {
        return User.builder()
                .id(UserId.of(userId))
                .fullName(FullName.of(saveUserBodyDto.getFirstName(), null, saveUserBodyDto.getLastName()))
                .phone(Phone.of(saveUserBodyDto.getPhone()))
                .build();
    }
}
