package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.domain.FullName;
import com.example.service.user.domain.Phone;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Mapper;

import java.time.LocalDateTime;

import static com.example.service.user.domain.UserFunctions.userFirstName;
import static com.example.service.user.domain.UserFunctions.userIdAsInt;
import static com.example.service.user.domain.UserFunctions.userLastName;
import static com.example.service.user.domain.UserFunctions.userPhoneNumber;

@Mapper
class UserJpaMapper {

    UserJpaMapper() {
        super();
    }

    UserData toJpaEntity(User user) {
        return UserData.builder()
                .id(userIdAsInt.apply(user))
                .firstName(userFirstName.apply(user))
                .lastName(userLastName.apply(user))
                .phone(userPhoneNumber.apply(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    UserData toJpaEntity(User user, UserData persistedUserData) {
        return persistedUserData.toBuilder()
                .firstName(userFirstName.apply(user))
                .lastName(userLastName.apply(user))
                .phone(userPhoneNumber.apply(user))
                .updatedAt(LocalDateTime.now())
                .build();
    }

    User toDomain(UserData userData) {
        return User.builder()
                .id(UserId.of(userData.getId()))
                .fullName(FullName.of(userData.getFirstName(), null, userData.getLastName()))
                .phone(Phone.of(userData.getPhone()))
                .build();
    }

}
