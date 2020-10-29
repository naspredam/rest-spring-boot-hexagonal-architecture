package com.example.hexagonal.arch.service.users.adapter.persistence;

import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.common.annotation.Adapter;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.persistence.WriteUserPort;
import reactor.core.publisher.Mono;

@Adapter
class ChangeUserAdapter implements WriteUserPort {

    private final UserRepository userRepository;

    public ChangeUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ReactiveOptional<User> saveNew(User user) {
        UserData userData = UserJpaMapper.toJpaEntity(user);
        Mono<UserData> userMono = userRepository.save(userData);
        return ReactiveOptional.of(userMono)
                .map(UserJpaMapper::toDomain);
    }

    @Override
    public ReactiveOptional<Void> deleteById(UserId userId) {
        Mono<Void> voidMono = userRepository.deleteById(userId.intValue());
        return ReactiveOptional.of(voidMono);
    }


}
