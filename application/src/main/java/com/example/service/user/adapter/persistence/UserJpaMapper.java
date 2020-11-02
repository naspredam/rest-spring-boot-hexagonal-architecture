package com.example.service.user.adapter.persistence;

import com.example.service.common.annotation.Mapper;
import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.domain.model.FullName;
import com.example.service.user.domain.model.Phone;
import com.example.service.user.domain.model.User;
import com.example.service.user.domain.model.UserId;

import java.time.LocalDateTime;

import static com.example.service.user.domain.model.UserFunctions.userFirstName;
import static com.example.service.user.domain.model.UserFunctions.userIdAsInt;
import static com.example.service.user.domain.model.UserFunctions.userLastName;
import static com.example.service.user.domain.model.UserFunctions.userPhoneNumber;

@Mapper
class UserJpaMapper {

    private UserJpaMapper() {
        super();
    }

    static UserData toJpaEntity(User user) {
        return UserData.builder()
                .id(userIdAsInt.apply(user))
                .firstName(userFirstName.apply(user))
                .lastName(userLastName.apply(user))
                .phone(userPhoneNumber.apply(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    static User toDomain(UserData userData) {
        return User.builder()
                .id(UserId.of(userData.getId()))
                .fullName(FullName.of(userData.getFirstName(), null, userData.getLastName()))
                .phone(Phone.of(userData.getPhone()))
                .build();
    }

}
