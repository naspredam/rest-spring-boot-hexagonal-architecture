package com.example.hexagonal.arch.service.users.adapter.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Table("users")
@EqualsAndHashCode
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserData {

    @Id
    private final Integer id;

    private final String firstName;

    private final String lastName;

    private final String phone;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private final LocalDateTime updatedAt;
}
