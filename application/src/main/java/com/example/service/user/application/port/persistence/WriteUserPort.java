package com.example.service.user.application.port.persistence;

import com.example.service.user.domain.model.User;
import com.example.service.user.domain.model.UserId;

public interface WriteUserPort {

    User saveNew(User user);

    void deleteById(UserId userId);
}
