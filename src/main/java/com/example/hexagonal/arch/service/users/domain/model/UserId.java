package com.example.hexagonal.arch.service.users.domain.model;

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
