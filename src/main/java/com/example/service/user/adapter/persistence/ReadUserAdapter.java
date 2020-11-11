package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.UnitReactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.service.user.domain.UserFunctions.userFirstName;
import static com.example.service.user.domain.UserFunctions.userLastName;

@Adapter
class ReadUserAdapter implements ReadUserPort {

    private final UserRepository userRepository;

    private final UserJpaMapper userJpaMapper;

    public ReadUserAdapter(UserRepository userRepository, UserJpaMapper userJpaMapper) {
        this.userRepository = userRepository;
        this.userJpaMapper = userJpaMapper;
    }

    @Override
    public UnitReactive<Boolean> existsUserByName(User user) {
        String firstName = userFirstName.apply(user);
        String lastName = userLastName.apply(user);
        Mono<Boolean> booleanMono = userRepository.findByFirstNameAndLastName(firstName, lastName)
                    .hasElements();
        return UnitReactive.of(booleanMono);
    }

    @Override
    public UnitReactive<Boolean> existsUserById(UserId userId) {
        Integer userIdAsInt = userId.intValue();
        Mono<Boolean> booleanMono = userRepository.existsById(userIdAsInt);
        return UnitReactive.of(booleanMono);
    }

    @Override
    public UnitReactive<User> fetchById(UserId userId) {
        Mono<UserData> byId = userRepository.findById(userId.intValue());
        return UnitReactive.of(byId)
                .map(userJpaMapper::toDomain);
    }

    @Override
    public CollectionReactive<User> fetchAll() {
        Flux<UserData> all = userRepository.findAll();
        return CollectionReactive.of(all)
                .map(userJpaMapper::toDomain);
    }
}
