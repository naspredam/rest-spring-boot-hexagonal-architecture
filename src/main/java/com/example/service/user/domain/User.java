package com.example.service.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
public class User implements Serializable {

    private final UserId id;

    @NotNull
    private final FullName fullName;

    @NotNull
    private final Phone phone;

}
