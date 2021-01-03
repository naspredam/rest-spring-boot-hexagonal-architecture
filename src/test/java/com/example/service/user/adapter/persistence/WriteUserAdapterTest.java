package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        Mockito.when(userRepository.save(newUserDataMapped)).thenReturn(persistedUserData);
        Mockito.when(userJpaMapper.toDomain(persistedUserData)).thenReturn(persistedUser);

        User userSaved = writeUserAdapter.saveNew(newUser);
        assertThat(userSaved).isEqualTo(persistedUser);
    }

    @Test
    public void shouldTryToUpdateUserButReturnsEmpty_whenNoUserFoundById() {
        User userToUpdate = fakeUser();

        Mockito.when(userRepository.findById(userIdAsInt.apply(userToUpdate)))
                .thenReturn(Optional.empty());

        Optional<User> update = writeUserAdapter.update(userToUpdate);
        assertThat(update).isEmpty();

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
                .thenReturn(Optional.of(persistedUserData));
        Mockito.when(userJpaMapper.toJpaEntity(userToUpdate, persistedUserData))
                .thenReturn(updatedUserFromDataMapped);
        Mockito.when(userRepository.save(updatedUserFromDataMapped))
                .thenReturn(updatedUserFromData);
        Mockito.when(userJpaMapper.toDomain(updatedUserFromData))
                .thenReturn(updatedUser);


        Optional<User> update = writeUserAdapter.update(userToUpdate);
        assertThat(update).isPresent().contains(updatedUser);
    }

    @Test
    public void shouldApplyDeleteUserById() {
        UserId userId = fakeUserId();

        writeUserAdapter.deleteById(userId);

        Mockito.verify(userRepository).deleteById(userId.intValue());
    }
}