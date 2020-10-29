package com.example.hexagonal.arch.service.common;

import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.example.hexagonal.arch.service.users.domain.model.FullName;
import com.example.hexagonal.arch.service.users.domain.model.Phone;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

import java.time.LocalDateTime;
import java.util.Locale;

public class DataFaker {

    private static final Faker FAKER = new Faker();

    private static final FakeValuesService FAKE_VALUES_SERVICE = new FakeValuesService(Locale.CANADA, new RandomService());

    public static UserId fakeUserId() {
        return UserId.of(fakeUserIdAsInt());
    }

    private static int fakeUserIdAsInt() {
        return FAKER.number().numberBetween(1, 10000);
    }

    public static FullName fakeFullName() {
        return FullName.of(FAKER.name().firstName(), FAKER.name().nameWithMiddle(), FAKER.name().lastName());
    }

    public static Phone fakePhone() {
        return Phone.of(fakePhoneNumberAsString());
    }

    private static String fakePhoneNumberAsString() {
        return FAKE_VALUES_SERVICE.regexify("\\(\\+[1-9]\\d{1,2}\\) [0-9]{1,3}[0-9\\-]{6,9}[0-9]{1}");
    }

    public static User fakeUser() {
        return User.builder()
                .id(DataFaker.fakeUserId())
                .fullName(fakeFullName())
                .phone(DataFaker.fakePhone())
                .build();
    }

    public static UserData fakeUserData() {
        return UserData.builder()
                .id(fakeUserIdAsInt())
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .phone(fakePhoneNumberAsString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
