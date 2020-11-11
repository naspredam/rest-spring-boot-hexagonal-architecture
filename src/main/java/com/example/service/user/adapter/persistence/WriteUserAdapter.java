package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;
import com.example.service.user.infrastructure.reactive.UnitReactive;
import reactor.core.publisher.Mono;

import static com.example.service.user.domain.UserFunctions.userIdAsInt;

@Adapter
class WriteUserAdapter implements WriteUserPort {

    private final UserRepository userRepository;

    private final UserJpaMapper userJpaMapper;

    public WriteUserAdapter(UserRepository userRepository, UserJpaMapper userJpaMapper) {
        this.userRepository = userRepository;
        this.userJpaMapper = userJpaMapper;
    }

    @Override
    public UnitReactive<User> saveNew(User user) {
        UserData userData = userJpaMapper.toJpaEntity(user);
        Mono<User> userMono = userRepository.save(userData)
                .map(userJpaMapper::toDomain);
        return UnitReactive.of(userMono);
    }

    @Override
    public UnitReactive<User> update(User user) {
        int userId = userIdAsInt.apply(user);
        Mono<UserData> userMono = userRepository.findById(userId)
                .map(persistedUserData -> userJpaMapper.toJpaEntity(user, persistedUserData))
                .flatMap(userRepository::save);
        return UnitReactive.of(userMono)
                .map(userJpaMapper::toDomain);
    }

    @Override
    public UnitReactive<Void> deleteById(UserId userId) {
        Mono<Void> voidMono = userRepository.deleteById(userId.intValue());
        return UnitReactive.of(voidMono);
    }


}
