package com.example.service.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Getter(AccessLevel.PACKAGE)
@Value(staticConstructor = "of")
public class UserId {

    @NotNull
    Integer intValue;

    public Integer intValue() {
        return intValue;
    }
}
