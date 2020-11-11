package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import com.example.service.user.domain.User;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.CollectionReactive;
import com.example.service.user.infrastructure.reactive.UnitReactive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

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
                .thenReturn(Flux.just(UserData.builder().build()));

        UnitReactive<Boolean> userFoundUnitReactive = readUserAdapter.existsUserByName(user);
        assertThat(userFoundUnitReactive.mono().block()).isTrue();
    }

    @Test
    public void shouldNotFoundUserByFirstAndLastName() {
        User user = fakeUser();
        String firstName = userFirstName.apply(user);
        String lastName = userLastName.apply(user);

        Mockito.when(userRepository.findByFirstNameAndLastName(firstName, lastName))
                .thenReturn(Flux.empty());

        UnitReactive<Boolean> userFoundUnitReactive = readUserAdapter.existsUserByName(user);
        assertThat(userFoundUnitReactive.mono().block()).isFalse();
    }

    @Test
    public void shouldFoundUserById() {
        User user = fakeUser();
        Integer userId = userIdAsInt.apply(user);

        Mockito.when(userRepository.existsById(userId))
                .thenReturn(Mono.just(true));

        UnitReactive<Boolean> userFoundUnitReactive = readUserAdapter.existsUserById(UserId.of(userId));
        assertThat(userFoundUnitReactive.mono().block()).isTrue();
    }

    @Test
    public void shouldNotFoundUserById() {
        User user = fakeUser();
        Integer userId = userIdAsInt.apply(user);

        Mockito.when(userRepository.existsById(userId))
                .thenReturn(Mono.just(false));

        UnitReactive<Boolean> userFoundUnitReactive = readUserAdapter.existsUserById(UserId.of(userId));
        assertThat(userFoundUnitReactive.mono().block()).isFalse();
    }

    @Test
    public void shouldReturnEmpty_whenUserNotFound() {
        UserId userId = fakeUserId();

        Mockito.when(userRepository.findById(userId.intValue()))
                .thenReturn(Mono.empty());

        UnitReactive<User> userOptional = readUserAdapter.fetchById(userId);
        assertThat(userOptional.mono().blockOptional()).isEmpty();
    }

    @Test
    public void shouldReturnUser_whenUserFound() {
        UserId userId = fakeUserId();
        UserData foundUserData = fakeUserDataBuilder().id(userId.intValue()).build();
        User user = fakeUserBuilder().id(userId).build();

        Mockito.when(userRepository.findById(foundUserData.getId()))
                .thenReturn(Mono.just(foundUserData));
        Mockito.when(userJpaMapper.toDomain(foundUserData)).thenReturn(user);

        UnitReactive<User> userUnitReactive = readUserAdapter.fetchById(userId);
        assertThat(userUnitReactive.mono().blockOptional()).isPresent().contains(user);
    }

    @Test
    public void shouldReturnEmptyUserList_whenNoUserFound() {
        Mockito.when(userRepository.findAll()).thenReturn(Flux.empty());

        CollectionReactive<User> userCollectionReactive = readUserAdapter.fetchAll();
        assertThat(userCollectionReactive.flux().collectList().block()).isEmpty();
    }

    @Test
    public void shouldReturnFilledUserList_whenUsersFound() {
        UserData userData1 = fakeUserData();
        UserData userData2 = fakeUserData();
        Flux<UserData> userDataFlux = Flux.just(userData1, userData2);

        User user1 = fakeUser();
        User user2 = fakeUser();

        Mockito.when(userRepository.findAll()).thenReturn(userDataFlux);
        Mockito.when(userJpaMapper.toDomain(userData1)).thenReturn(user1);
        Mockito.when(userJpaMapper.toDomain(userData2)).thenReturn(user2);

        CollectionReactive<User> userCollectionReactive = readUserAdapter.fetchAll();
        Collection<User> users = userCollectionReactive.flux().collectList().block();
        assertThat(users).hasSize(2).containsExactlyInAnyOrder(user1, user2);
    }
}