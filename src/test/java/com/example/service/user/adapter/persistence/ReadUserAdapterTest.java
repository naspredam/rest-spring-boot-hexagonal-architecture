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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.service.user.domain.UserFunctions.userFirstName;
import static com.example.service.user.domain.UserFunctions.userIdAsInt;
import static com.example.service.user.domain.UserFunctions.userLastName;
import static com.example.service.user.utils.DataFaker.fakeUser;
import static com.example.service.user.utils.DataFaker.fakeUserBuilder;
import static com.example.service.user.utils.DataFaker.fakeUserData;
import static com.example.service.user.utils.DataFaker.fakeUserDataBuilder;
import static com.example.service.user.utils.DataFaker.fakeUserId;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReadUserAdapterTest {

    @InjectMocks
    private ReadUserAdapter readUserAdapter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserJpaMapper userJpaMapper;

    @Test
    public void shouldFoundUserByFirstAndLastName() {
        User user = fakeUser();
        String firstName = userFirstName.apply(user);
        String lastName = userLastName.apply(user);

        Mockito.when(userRepository.findByFirstNameAndLastName(firstName, lastName))
                .thenReturn(List.of(UserData.builder().build()));

        Boolean userFound = readUserAdapter.existsUserByName(user);
        assertThat(userFound).isTrue();
    }

    @Test
    public void shouldNotFoundUserByFirstAndLastName() {
        User user = fakeUser();
        String firstName = userFirstName.apply(user);
        String lastName = userLastName.apply(user);

        Mockito.when(userRepository.findByFirstNameAndLastName(firstName, lastName))
                .thenReturn(List.of());

        Boolean userFoundUnitReactive = readUserAdapter.existsUserByName(user);
        assertThat(userFoundUnitReactive).isFalse();
    }

    @Test
    public void shouldFoundUserById() {
        User user = fakeUser();
        Integer userId = userIdAsInt.apply(user);

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        Boolean userFoundUnitReactive = readUserAdapter.existsUserById(UserId.of(userId));
        assertThat(userFoundUnitReactive).isTrue();
    }

    @Test
    public void shouldNotFoundUserById() {
        User user = fakeUser();
        Integer userId = userIdAsInt.apply(user);

        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Boolean userFoundUnitReactive = readUserAdapter.existsUserById(UserId.of(userId));
        assertThat(userFoundUnitReactive).isFalse();
    }

    @Test
    public void shouldReturnEmpty_whenUserNotFound() {
        UserId userId = fakeUserId();

        Mockito.when(userRepository.findById(userId.intValue()))
                .thenReturn(Optional.empty());

        Optional<User> userOptional = readUserAdapter.fetchById(userId);
        assertThat(userOptional).isEmpty();
    }

    @Test
    public void shouldReturnUser_whenUserFound() {
        UserId userId = fakeUserId();
        UserData foundUserData = fakeUserDataBuilder().id(userId.intValue()).build();
        User user = fakeUserBuilder().id(userId).build();

        Mockito.when(userRepository.findById(foundUserData.getId()))
                .thenReturn(Optional.of(foundUserData));
        Mockito.when(userJpaMapper.toDomain(foundUserData)).thenReturn(user);

        Optional<User> userOptional = readUserAdapter.fetchById(userId);
        assertThat(userOptional).isPresent().contains(user);
    }

    @Test
    public void shouldReturnEmptyUserList_whenNoUserFound() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of());

        Collection<User> userCollection = readUserAdapter.fetchAll();
        assertThat(userCollection).isEmpty();
    }

    @Test
    public void shouldReturnFilledUserList_whenUsersFound() {
        UserData userData1 = fakeUserData();
        UserData userData2 = fakeUserData();
        List<UserData> userDataList = List.of(userData1, userData2);

        User user1 = fakeUser();
        User user2 = fakeUser();

        Mockito.when(userRepository.findAll()).thenReturn(userDataList);
        Mockito.when(userJpaMapper.toDomain(userData1)).thenReturn(user1);
        Mockito.when(userJpaMapper.toDomain(userData2)).thenReturn(user2);

        Collection<User> users = readUserAdapter.fetchAll();
        assertThat(users).hasSize(2).containsExactlyInAnyOrder(user1, user2);
    }
}