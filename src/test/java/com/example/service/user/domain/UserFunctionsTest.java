package com.example.service.user.domain;

import com.example.service.user.utils.DataFaker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserFunctionsTest {

    @Test
    public void shouldHaveNoId() {
        User user = User.builder()
                .fullName(DataFaker.fakeFullName())
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(UserFunctions.userIdAsInt.apply(user)).isNull();
    }

    @Test
    public void shouldHaveInformedId() {
        UserId userId = DataFaker.fakeUserId();
        User user = User.builder()
                .id(userId)
                .fullName(DataFaker.fakeFullName())
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(UserFunctions.userIdAsInt.apply(user)).isEqualTo(userId.intValue());
    }

    @Test
    public void shouldReturnFirstName() {
        FullName fullName = DataFaker.fakeFullName();
        User user = User.builder()
                .id(DataFaker.fakeUserId())
                .fullName(fullName)
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(UserFunctions.userFirstName.apply(user)).isEqualTo(fullName.getFirstName());
    }

    @Test
    public void shouldReturnLastName() {
        FullName fullName = DataFaker.fakeFullName();
        User user = User.builder()
                .id(DataFaker.fakeUserId())
                .fullName(fullName)
                .phone(DataFaker.fakePhone())
                .build();

        assertThat(UserFunctions.userLastName.apply(user)).isEqualTo(fullName.getLastName());
    }

    @Test
    public void shouldReturnPhoneNumber() {
        Phone phone = DataFaker.fakePhone();
        User user = User.builder()
                .id(DataFaker.fakeUserId())
                .fullName(DataFaker.fakeFullName())
                .phone(phone)
                .build();

        assertThat(UserFunctions.userPhoneNumber.apply(user)).isEqualTo(phone.number());
    }
}