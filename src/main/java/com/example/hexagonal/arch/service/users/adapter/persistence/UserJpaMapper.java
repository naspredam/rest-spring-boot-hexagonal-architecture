package com.example.hexagonal.arch.service.users.adapter.persistence;

import com.example.hexagonal.arch.service.common.annotation.Mapper;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import com.example.hexagonal.arch.service.users.domain.model.FullName;
import com.example.hexagonal.arch.service.users.domain.model.Phone;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserFunctions;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

import java.time.LocalDateTime;

@Mapper
class UserJpaMapper {

    private UserJpaMapper() {
        super();
    }

    static UserData toJpaEntity(User user) {
        return UserData.builder()
                .id(UserFunctions.userIdAsInt.apply(user))
                .firstName(UserFunctions.userFirstName.apply(user))
                .lastName(UserFunctions.userLastName.apply(user))
                .phone(UserFunctions.userPhoneNumber.apply(user))
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
