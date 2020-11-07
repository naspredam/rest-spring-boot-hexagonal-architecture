package com.example.service.user.application.usecase;

import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.SingleReactive;

public interface DeleteUsersByIdUseCase {

    SingleReactive<Void> deleteById(UserId userId);

}
