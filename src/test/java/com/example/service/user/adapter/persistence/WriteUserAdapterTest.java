package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.UnitReactive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static com.example.service.user.domain.UserFunctions.userIdAsInt;
import static com.example.service.user.utils.DataFaker.fakeUser;
import static com.example.service.user.utils.DataFaker.fakeUserBuilder;
import static com.example.service.user.utils.DataFaker.fakeUserData;
import static com.example.service.user.utils.DataFaker.fakeUserDataBuilder;
import static com.example.service.user.utils.DataFaker.fakeUserId;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WriteUserAdapterTest {

    @InjectMocks
    private WriteUserAdapter writeUserAdapter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserJpaMapper userJpaMapper;

    @Test
    public void shouldSaveNewUser() {
        User newUser = fakeUserBuilder().id(null).build();
        UserData newUserDataMapped = fakeUserDataBuilder().id(null).build();
        UserData persistedUserData = fakeUserData();
        User persistedUser = fakeUser();

        Mockito.when(userJpaMapper.toJpaEntity(newUser)).thenReturn(newUserDataMapped);
        Mockito.when(userRepository.save(newUserDataMapped)).thenReturn(Mono.just(persistedUserData));
        Mockito.when(userJpaMapper.toDomain(persistedUserData)).thenReturn(persistedUser);

        UnitReactive<User> userUnitReactive = writeUserAdapter.saveNew(newUser);
        assertThat(userUnitReactive.mono().block()).isEqualTo(persistedUser);
    }

    @Test
    public void shouldTryToUpdateUserButReturnsEmpty_whenNoUserFoundById() {
        User userToUpdate = fakeUser();

        Mockito.when(userRepository.findById(userIdAsInt.apply(userToUpdate)))
                .thenReturn(Mono.empty());

        UnitReactive<User> update = writeUserAdapter.update(userToUpdate);
        assertThat(update.mono().blockOptional()).isEmpty();

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void shouldUpdateUserReturningUpdatedValue_whenUserFoundById() {
        User userToUpdate = fakeUser();
        UserData persistedUserData = fakeUserData();
        UserData updatedUserFromDataMapped = fakeUserData();
        UserData updatedUserFromData = fakeUserData();
        User updatedUser = fakeUser();

        Mockito.when(userRepository.findById(userIdAsInt.apply(userToUpdate)))
                .thenReturn(Mono.just(persistedUserData));
        Mockito.when(userJpaMapper.toJpaEntity(userToUpdate, persistedUserData))
                .thenReturn(updatedUserFromDataMapped);
        Mockito.when(userRepository.save(updatedUserFromDataMapped))
                .thenReturn(Mono.just(updatedUserFromData));
        Mockito.when(userJpaMapper.toDomain(updatedUserFromData))
                .thenReturn(updatedUser);


        UnitReactive<User> update = writeUserAdapter.update(userToUpdate);
        assertThat(update.mono().blockOptional()).isPresent().contains(updatedUser);
    }

    @Test
    public void shouldApplyDeleteUserById() {
        UserId userId = fakeUserId();

        Mockito.when(userRepository.deleteById(userId.intValue())).thenReturn(Mono.empty());

        UnitReactive<Void> voidUnitReactive = writeUserAdapter.deleteById(userId);
        voidUnitReactive.mono().block();

        Mockito.verify(userRepository).deleteById(userId.intValue());
    }
}