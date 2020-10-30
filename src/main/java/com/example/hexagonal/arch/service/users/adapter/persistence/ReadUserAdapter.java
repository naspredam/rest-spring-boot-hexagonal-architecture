package com.example.hexagonal.arch.service.users.adapter.persistence;

import com.example.hexagonal.arch.service.common.annotation.Adapter;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.persistence.ReadUserPort;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userFirstName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userLastName;

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
