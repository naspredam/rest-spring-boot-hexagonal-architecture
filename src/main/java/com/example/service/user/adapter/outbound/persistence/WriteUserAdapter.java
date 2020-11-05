package com.example.service.user.adapter.outbound.persistence;

import com.example.service.user.adapter.outbound.persistence.model.UserData;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.annotations.Adapter;

import javax.persistence.EntityNotFoundException;

import static com.example.service.user.domain.UserFunctions.userIdAsInt;

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
    public User update(User user) {
        int userId = userIdAsInt.apply(user);
        UserData persistedUserData = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        UserData userDataToPersist = UserJpaMapper.toJpaEntity(user, persistedUserData);
        UserData userDataPersisted = userRepository.save(userDataToPersist);
        return UserJpaMapper.toDomain(userDataPersisted);
    }

    @Override
    public void deleteById(UserId userId) {
        userRepository.deleteById(userId.intValue());
    }


}
