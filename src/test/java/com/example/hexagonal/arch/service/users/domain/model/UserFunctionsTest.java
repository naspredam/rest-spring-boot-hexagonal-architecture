package com.example.hexagonal.arch.service.users.domain.model;

import com.example.hexagonal.arch.service.common.DataFaker;
import org.junit.jupiter.api.Test;

import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userFirstName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userIdAsInt;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userLastName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userPhoneNumber;
import static org.assertj.core.api.Assertions.assertThat;

class UserFunctionsTest {

    @Test
    public void shouldHaveNoId() {
        User user = User.builder()
                .fullName(DataFaker.fakeFullName())
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(userIdAsInt.apply(user)).isNull();
    }

    @Test
    public void shouldHaveInformedId() {
        UserId userId = DataFaker.fakeUserId();
        User user = User.builder()
                .id(userId)
                .fullName(DataFaker.fakeFullName())
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(userIdAsInt.apply(user)).isEqualTo(userId.getIntValue());
    }

    @Test
    public void shouldReturnFirstName() {
        FullName fullName = DataFaker.fakeFullName();
        User user = User.builder()
                .id(DataFaker.fakeUserId())
                .fullName(fullName)
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(userFirstName.apply(user)).isEqualTo(fullName.getFirstName());
    }

    @Test
    public void shouldReturnLastName() {
        FullName fullName = DataFaker.fakeFullName();
        User user = User.builder()
                .id(DataFaker.fakeUserId())
                .fullName(fullName)
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(userLastName.apply(user)).isEqualTo(fullName.getLastName());
    }

    @Test
    public void shouldReturnPhoneNumber() {
        Phone phone = DataFaker.fakePhone();
        User user = User.builder()
                .id(DataFaker.fakeUserId())
                .fullName(DataFaker.fakeFullName())
                .phone(phone)
                .build();

        assertThat(userPhoneNumber.apply(user)).isEqualTo(phone.number());
    }
}