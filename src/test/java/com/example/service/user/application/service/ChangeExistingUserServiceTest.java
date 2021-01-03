package com.example.service.user.application.service;

import com.example.service.user.application.port.persistence.WriteUserPort;
import com.example.service.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import java.util.Optional;

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

        Mockito.when(writeUserPort.update(user)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> changeExistingUserService.updateUser(user))
                .isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(writeUserPort, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    public void shouldAttemptToSaveTheUserThroughPort_whenUserIsValid() {
        User user = fakeUserBuilder().id(null).build();

        User userFromPort = user.toBuilder().id(fakeUserId()).build();
        Mockito.when(writeUserPort.update(user)).thenReturn(Optional.of(userFromPort));

        User updatedUser = changeExistingUserService.updateUser(user);
        assertThat(updatedUser).isEqualTo(userFromPort);
    }

}