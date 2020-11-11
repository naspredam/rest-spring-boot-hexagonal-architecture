package com.example.service.user.application.usecase;

import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.UnitReactive;

public interface DeleteUsersByIdUseCase {

    UnitReactive<Void> deleteById(UserId userId);

}
