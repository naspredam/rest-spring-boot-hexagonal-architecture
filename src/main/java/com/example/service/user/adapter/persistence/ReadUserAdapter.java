package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.service.user.domain.UserFunctions.userFirstName;
import static com.example.service.user.domain.UserFunctions.userLastName;

@Adapter
class ReadUserAdapter implements ReadUserPort {

    private final UserRepository userRepository;

    public ReadUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsUserByName(User user) {
        Collection<UserData> byFirstNameAndLastName = userRepository.findByFirstNameAndLastName(userFirstName.apply(user), userLastName.apply(user));
        return !byFirstNameAndLastName.isEmpty();
    }

    @Override
    public boolean existsUserById(UserId userId) {
        return userRepository.existsById(userId.intValue());
    }

    @Override
    public Optional<User> fetchById(UserId userId) {
        return userRepository.findById(userId.intValue())
                .map(UserJpaMapper::toDomain);
    }

    @Override
    public Collection<User> fetchAll() {
        return userRepository.findAll().stream()
                .map(UserJpaMapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}
