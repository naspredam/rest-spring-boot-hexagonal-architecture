package com.example.hexagonal.arch.service.users.adapter.persistence;

import com.example.hexagonal.arch.service.common.annotation.Adapter;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserId;
import com.example.hexagonal.arch.service.users.domain.port.persistence.WriteUserPort;

@Adapter
class WriteUserAdapter implements WriteUserPort {

    private final UserRepository userRepository;

    public WriteUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveNew(User user) {
        UserData userData = UserJpaMapper.toJpaEntity(user);
        UserData userDataPersisted = userRepository.save(userData);
        return UserJpaMapper.toDomain(userDataPersisted);
    }

    @Override
    public void deleteById(UserId userId) {
        userRepository.deleteById(userId.intValue());
    }


}
