package com.example.service.user.adapter.outbound.persistence;

import com.example.service.user.adapter.outbound.persistence.model.UserData;
import com.example.service.user.application.port.outbound.persistence.ReadUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.SingleReactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public SingleReactive<Boolean> existsUserByName(User user) {
        String firstName = userFirstName.apply(user);
        String lastName = userLastName.apply(user);
        Mono<Boolean> booleanMono = userRepository.findByFirstNameAndLastName(firstName, lastName)
                    .hasElements();
        return SingleReactive.of(booleanMono);
    }

    @Override
    public SingleReactive<Boolean> existsUserById(UserId userId) {
        Integer userIdAsInt = userId.intValue();
        Mono<Boolean> booleanMono = userRepository.existsById(userIdAsInt);
        return SingleReactive.of(booleanMono);
    }

    @Override
    public SingleReactive<User> fetchById(UserId userId) {
        Mono<UserData> byId = userRepository.findById(userId.intValue());
        return SingleReactive.of(byId)
                .map(userJpaMapper::toDomain);
    }

    @Override
    public CollectionReactive<User> fetchAll() {
        Flux<UserData> all = userRepository.findAll();
        return CollectionReactive.of(all)
                .map(userJpaMapper::toDomain);
    }
}
