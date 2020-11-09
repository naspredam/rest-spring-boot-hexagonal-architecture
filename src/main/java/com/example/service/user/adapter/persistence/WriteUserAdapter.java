package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.application.port.outbound.persistence.WriteUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;
import com.example.service.user.infrastructure.reactive.SingleReactive;
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
    public SingleReactive<User> saveNew(User user) {
        UserData userData = userJpaMapper.toJpaEntity(user);
        Mono<User> userMono = userRepository.save(userData)
                .map(userJpaMapper::toDomain);
        return SingleReactive.of(userMono);
    }

    @Override
    public SingleReactive<User> update(User user) {
        int userId = userIdAsInt.apply(user);
        Mono<UserData> userMono = userRepository.findById(userId)
                .map(persistedUserData -> userJpaMapper.toJpaEntity(user, persistedUserData))
                .flatMap(userRepository::save);
        return SingleReactive.of(userMono)
                .map(userJpaMapper::toDomain);
    }

    @Override
    public SingleReactive<Void> deleteById(UserId userId) {
        Mono<Void> voidMono = userRepository.deleteById(userId.intValue());
        return SingleReactive.of(voidMono);
    }


}
