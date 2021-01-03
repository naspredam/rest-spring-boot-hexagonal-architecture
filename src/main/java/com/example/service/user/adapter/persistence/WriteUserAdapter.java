package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;

import java.util.Optional;

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
    public User saveNew(User user) {
        UserData userData = userJpaMapper.toJpaEntity(user);
        UserData userSaved = userRepository.save(userData);
        return userJpaMapper.toDomain(userSaved);
    }

    @Override
    public Optional<User> update(User user) {
        int userId = userIdAsInt.apply(user);
        return userRepository.findById(userId)
                .map(persistedUserData -> userJpaMapper.toJpaEntity(user, persistedUserData))
                .map(userRepository::save)
                .map(userJpaMapper::toDomain);
    }

    @Override
    public void deleteById(UserId userId) {
        userRepository.deleteById(userId.intValue());
    }


}
