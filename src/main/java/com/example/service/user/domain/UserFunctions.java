package com.example.service.user.domain;

import java.util.Objects;
import java.util.function.Function;

public class UserFunctions {

    private UserFunctions() {
        super();
    }

    public static Function<User, Integer> userIdAsInt =
            user -> Objects.isNull(user.getId()) ? null : user.getId().getIntValue();

    private static final Function<FullName, String> firstName = FullName::getFirstName;

    private static final Function<FullName, String> lastName = FullName::getLastName;

    private static final Function<Phone, String> phoneNumber = Phone::number;

    public static Function<User, String> userFirstName = firstName.compose(User::getFullName);

    public static Function<User, String> userLastName = lastName.compose(User::getFullName);

    public static Function<User, String> userPhoneNumber = phoneNumber.compose(User::getPhone);

}
