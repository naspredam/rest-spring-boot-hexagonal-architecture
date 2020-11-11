package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.User;
import com.example.service.user.infrastructure.reactive.UnitReactive;
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
class ChangeExistingUserServiceTest {

    @InjectMocks
    private ChangeExistingUserService changeExistingUserService;

    @Mock
    private WriteUserPort writeUserPort;

    @Test
    public void shouldThrowException_whenUserIsNotValid() {
        User user = fakeUserBuilder().fullName(null).build();

        assertThatThrownBy(() -> changeExistingUserService.updateUser(user))
            .isInstanceOf(ConstraintViolationException.class);
        Mockito.verify(writeUserPort, Mockito.never()).update(Mockito.any());
    }

    @Test
    public void shouldReturnEmpty_whenUserIsNotFound() {
        User user = fakeUserBuilder().build();

        Mockito.when(writeUserPort.update(user)).thenReturn(UnitReactive.of(Mono.empty()));

        UnitReactive<User> userUnitReactive = changeExistingUserService.updateUser(user);
        assertThat(userUnitReactive.mono().blockOptional()).isEmpty();
        Mockito.verify(writeUserPort, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    public void shouldAttemptToSaveTheUserThroughPort_whenUserIsValid() {
        User user = fakeUserBuilder().id(null).build();

        User userFromPort = user.toBuilder().id(fakeUserId()).build();
        Mockito.when(writeUserPort.update(user)).thenReturn(UnitReactive.of(Mono.just(userFromPort)));

        UnitReactive<User> userUnitReactive = changeExistingUserService.updateUser(user);
        assertThat(userUnitReactive.mono().block()).isEqualTo(userFromPort);
    }

}