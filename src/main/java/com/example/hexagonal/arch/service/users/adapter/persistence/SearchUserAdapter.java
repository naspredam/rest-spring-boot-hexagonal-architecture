package com.example.hexagonal.arch.service.users.adapter.persistence;

import com.example.hexagonal.arch.service.common.reactive.ReactiveCollection;
import com.example.hexagonal.arch.service.common.annotation.Adapter;
import com.example.hexagonal.arch.service.common.reactive.ReactiveOptional;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.persistence.ReadUserPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userFirstName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userLastName;

@Adapter
class SearchUserAdapter implements ReadUserPort {

    private final UserRepository userRepository;

    public SearchUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ReactiveOptional<Boolean> existsUserByName(User user) {
        Flux<UserData> byFirstNameAndLastName = userRepository.findByFirstNameAndLastName(userFirstName.apply(user), userLastName.apply(user));
        Mono<Boolean> userExistsMono = byFirstNameAndLastName.hasElements();
        return ReactiveOptional.of(userExistsMono);
    }

    @Override
    public ReactiveOptional<Boolean> existsUserById(UserId userId) {
        Mono<Boolean> userExistsMono = userRepository.existsById(userId.intValue());
        return ReactiveOptional.of(userExistsMono);
    }

    @Override
    public ReactiveOptional<User> fetchById(UserId userId) {
        Mono<User> byId = userRepository.findById(userId.intValue()).map(UserJpaMapper::toDomain);
        return ReactiveOptional.of(byId);
    }

    @Override
    public ReactiveCollection<User> fetchAll() {
        Flux<User> execute = userRepository.findAll().map(UserJpaMapper::toDomain);
        return ReactiveCollection.of(execute);
    }
}
