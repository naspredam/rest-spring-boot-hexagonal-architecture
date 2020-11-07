package com.example.service.user.application.service;

import com.example.service.user.application.port.outbound.persistence.ReadUserPort;
import com.example.service.user.application.port.outbound.persistence.WriteUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.infrastructure.reactive.SingleReactive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;

import static com.example.service.user.utils.DataFaker.fakeUserBuilder;
import static com.example.service.user.utils.DataFaker.fakeUserId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SubmitNewUserServiceTest {

    @InjectMocks
    private SubmitNewUserService submitNewUserService;

    @Mock
    private WriteUserPort writeUserPort;

    @Mock
    private ReadUserPort readUserPort;

    @Test
    public void shouldThrowException_whenUserDomainObjectIsInvalid() {
        User user = fakeUserBuilder().fullName(null).build();

        assertThatThrownBy(() -> submitNewUserService.saveUser(user))
                .isInstanceOf(ConstraintViolationException.class);
        Mockito.verify(writeUserPort, Mockito.never()).saveNew(Mockito.any());
        Mockito.verify(readUserPort, Mockito.never()).existsUserByName(Mockito.any());
    }

    @Test
    public void shouldThrowException_whenUserNameIsAlreadyPersisted_AvoidingDuplicates() {
        User user = fakeUserBuilder().build();

        Mockito.when(readUserPort.existsUserByName(user)).thenReturn(SingleReactive.of(Mono.just(true)));

        SingleReactive<User> userSingleReactive = submitNewUserService.saveUser(user);
        assertThatThrownBy(() -> userSingleReactive.mono().blockOptional())
                .isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(writeUserPort, Mockito.never()).saveNew(Mockito.any());
        Mockito.verify(readUserPort, Mockito.times(1)).existsUserByName(Mockito.any());
    }

    @Test
    public void shouldProcessOnUserCreation_whenUserIsValid_AndNotDuplicated() {
        User user = fakeUserBuilder().build();
        User savedUser = user.toBuilder().id(fakeUserId()).build();

        Mockito.when(readUserPort.existsUserByName(user)).thenReturn(SingleReactive.of(Mono.just(false)));
        Mockito.when(writeUserPort.saveNew(user)).thenReturn(SingleReactive.of(Mono.just(savedUser)));

        SingleReactive<User> userSingleReactive = submitNewUserService.saveUser(user);
        assertThat(userSingleReactive.mono().blockOptional()).isPresent().contains(savedUser);

        Mockito.verify(writeUserPort, Mockito.times(1)).saveNew(Mockito.any());
        Mockito.verify(readUserPort, Mockito.times(1)).existsUserByName(Mockito.any());
    }

}