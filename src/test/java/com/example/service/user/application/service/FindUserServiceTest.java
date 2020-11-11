package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.ReadUserPort;
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

import javax.validation.ConstraintViolationException;

import java.util.Collection;

import static com.example.service.user.utils.DataFaker.fakeUser;
import static com.example.service.user.utils.DataFaker.fakeUserId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class FindUserServiceTest {

    @InjectMocks
    private FindUserService findUserService;

    @Mock
    private ReadUserPort readUserPort;

    @Test
    public void shouldThrowException_whenFindingById_withInvalidUserId() {
        UserId userId = UserId.of(null);

        assertThatThrownBy(() -> findUserService.findById(userId))
                .isInstanceOf(ConstraintViolationException.class);
        Mockito.verify(readUserPort, Mockito.never()).fetchById(Mockito.any());
    }

    @Test
    public void shouldReturnEmptyByIdUser_whenNotFound() {
        UserId userId = fakeUserId();

        Mockito.when(readUserPort.fetchById(userId)).thenReturn(UnitReactive.of(Mono.empty()));

        UnitReactive<User> byId = findUserService.findById(userId);
        assertThat(byId.mono().blockOptional()).isEmpty();
        Mockito.verify(readUserPort, Mockito.times(1)).fetchById(Mockito.any());
    }

    @Test
    public void shouldReturnUserById_whenFound() {
        UserId userId = fakeUserId();
        User userFromPort = fakeUser();

        Mockito.when(readUserPort.fetchById(userId)).thenReturn(UnitReactive.of(Mono.just(userFromPort)));

        UnitReactive<User> byId = findUserService.findById(userId);
        assertThat(byId.mono().block()).isEqualTo(userFromPort);
        Mockito.verify(readUserPort, Mockito.times(1)).fetchById(Mockito.any());
    }

    @Test
    public void shouldReturnEmpty_whenNoUsersFound() {
        Mockito.when(readUserPort.fetchAll()).thenReturn(CollectionReactive.of(Flux.empty()));

        CollectionReactive<User> userCollectionReactive = findUserService.fetchAllPersisted();
        Collection<User> users = userCollectionReactive.flux().collectList().block();
        assertThat(users).isEmpty();
    }

    @Test
    public void shouldReturnUserList_whenUsersPersisted() {
        User user1 = fakeUser();
        User user2 = fakeUser();
        CollectionReactive<User> userList = CollectionReactive.of(Flux.just(user1, user2));

        Mockito.when(readUserPort.fetchAll()).thenReturn(userList);

        CollectionReactive<User> userCollectionReactive = findUserService.fetchAllPersisted();
        Collection<User> users = userCollectionReactive.flux().collectList().block();
        assertThat(users).hasSize(2).containsExactlyInAnyOrder(user1, user2);
    }

}