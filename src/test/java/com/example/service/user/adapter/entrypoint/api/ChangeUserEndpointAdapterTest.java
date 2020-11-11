package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.application.usecase.ChangeExistingUserUseCase;
import com.example.service.user.application.usecase.DeleteUsersByIdUseCase;
import com.example.service.user.application.usecase.SubmitNewUserUseCase;
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

import static com.example.service.user.utils.DataFaker.fakeSaveUserBodyDto;
import static com.example.service.user.utils.DataFaker.fakeUser;
import static com.example.service.user.utils.DataFaker.fakeUserDto;
import static com.example.service.user.utils.DataFaker.fakeUserId;
import static com.example.service.user.utils.DataFaker.fakeUserIdAsInt;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ChangeUserEndpointAdapterTest {

    @InjectMocks
    private ChangeUserEndpointAdapter changeUserEndpointAdapter;

    @Mock
    private SubmitNewUserUseCase submitNewUserUseCase;

    @Mock
    private ChangeExistingUserUseCase changeExistingUserUseCase;

    @Mock
    private DeleteUsersByIdUseCase deleteUsersByIdUseCase;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Test
    public void shouldSaveNewUser_returningUserData() {
        SaveUserBodyDto saveUserBodyDto = fakeSaveUserBodyDto();
        User userTransformedFromSaveUserDto = fakeUser();
        User userCreated = fakeUser();
        UnitReactive<User> userCreatedUnitReactive = UnitReactive.of(Mono.just(userCreated));
        UserDto userDtoConvertedFromSaveUser = fakeUserDto();

        Mockito.when(userDtoMapper.toDomainFromSaveBody(saveUserBodyDto)).thenReturn(userTransformedFromSaveUserDto);
        Mockito.when(submitNewUserUseCase.saveUser(userTransformedFromSaveUserDto)).thenReturn(userCreatedUnitReactive);
        Mockito.when(userDtoMapper.toDto(userCreated)).thenReturn(userDtoConvertedFromSaveUser);

        UnitReactive<UserDto> userDtoUnitReactive = changeUserEndpointAdapter.saveUser(saveUserBodyDto);
        assertThat(userDtoUnitReactive.mono().block()).isEqualTo(userDtoConvertedFromSaveUser);
    }

    @Test
    public void shouldUpdateUser_returningUserData() {
        int userId = fakeUserIdAsInt();
        SaveUserBodyDto saveUserBodyDto = fakeSaveUserBodyDto();
        User userTransformedFromSaveUserDto = fakeUser();
        User userCreated = fakeUser();
        UnitReactive<User> userCreatedUnitReactive = UnitReactive.of(Mono.just(userCreated));
        UserDto userDtoConvertedFromSaveUser = fakeUserDto();

        Mockito.when(userDtoMapper.toDomainFromSaveBody(userId, saveUserBodyDto)).thenReturn(userTransformedFromSaveUserDto);
        Mockito.when(changeExistingUserUseCase.updateUser(userTransformedFromSaveUserDto)).thenReturn(userCreatedUnitReactive);
        Mockito.when(userDtoMapper.toDto(userCreated)).thenReturn(userDtoConvertedFromSaveUser);

        UnitReactive<UserDto> userDtoUnitReactive = changeUserEndpointAdapter.updateUser(userId, saveUserBodyDto);
        assertThat(userDtoUnitReactive.mono().block()).isEqualTo(userDtoConvertedFromSaveUser);
    }

    @Test
    public void shouldDeleteUser_returningVoid() {
        UserId userId = fakeUserId();

        Mockito.when(deleteUsersByIdUseCase.deleteById(userId)).thenReturn(UnitReactive.of(Mono.empty()));

        UnitReactive<Void> voidUnitReactive = changeUserEndpointAdapter.deleteUser(userId.intValue());
        voidUnitReactive.mono().block();

        Mockito.verify(deleteUsersByIdUseCase).deleteById(userId);
    }

}