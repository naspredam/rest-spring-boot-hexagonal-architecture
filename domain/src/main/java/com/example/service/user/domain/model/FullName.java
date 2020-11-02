package com.example.service.user.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Getter
@Value(staticConstructor = "of")
public class FullName {

    @NotNull
    String firstName;

    String middleName;

    @NotNull
    String lastName;

}
