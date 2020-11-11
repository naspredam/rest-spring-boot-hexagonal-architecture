package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.ReadUserPort;
import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.UserId;
import com.example.service.user.infrastructure.reactive.UnitReactive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;

import static com.example.service.user.utils.DataFaker.fakeUserId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class DeleteUsersByIdServiceTest {

    @InjectMocks
    private DeleteUsersByIdService deleteUsersByIdService;

    @Mock
    private WriteUserPort writeUserPort;

    @Mock
    private ReadUserPort readUserPort;

    @Test
    public void shouldThrowException_whenUserIdIsInvalidDomainObject() {
        UserId userId = UserId.of(null);

        assertThatThrownBy(() -> deleteUsersByIdService.deleteById(userId))
                .isInstanceOf(ConstraintViolationException.class);
        Mockito.verify(writeUserPort, Mockito.never()).deleteById(Mockito.any());
        Mockito.verify(readUserPort, Mockito.never()).existsUserById(Mockito.any());
    }

    @Test
    public void shouldThrowException_whenUserDoesNotExists() {
        UserId userId = fakeUserId();

        Mockito.when(readUserPort.existsUserById(userId)).thenReturn(UnitReactive.of(Mono.just(false)));
        UnitReactive<Void> voidUnitReactive = deleteUsersByIdService.deleteById(userId);
        assertThatThrownBy(() -> voidUnitReactive.mono().blockOptional())
            .isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(writeUserPort, Mockito.never()).deleteById(Mockito.any());
        Mockito.verify(readUserPort, Mockito.times(1)).existsUserById(Mockito.any());
    }

    @Test
    public void shouldApplyDeleteAction_whenUserExistsAndValidUserIdProvided() {
        UserId userId = fakeUserId();

        Mockito.when(readUserPort.existsUserById(userId)).thenReturn(UnitReactive.of(Mono.just(true)));
        Mockito.when(writeUserPort.deleteById(userId)).thenReturn(UnitReactive.of(Mono.empty()));

        UnitReactive<Void> voidUnitReactive = deleteUsersByIdService.deleteById(userId);
        voidUnitReactive.mono().block();

        Mockito.verify(writeUserPort, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(readUserPort, Mockito.times(1)).existsUserById(Mockito.any());
    }

}