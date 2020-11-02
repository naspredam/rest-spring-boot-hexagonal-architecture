package com.example.service.user.adapter.persistence;

import com.example.service.common.annotation.Adapter;
import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.model.User;
import com.example.service.user.domain.model.UserId;

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
