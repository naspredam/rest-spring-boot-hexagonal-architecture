package com.example.hexagonal.arch.service.users.domain.port.persistence;

import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;

public interface WriteUserPort {

    User saveNew(User user);

    void deleteById(UserId userId);
}
