package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.application.usecase.FindAllUsersUseCase;
import com.example.service.user.application.usecase.FindUserByIdUseCase;
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

import static com.example.service.user.utils.DataFaker.fakeUser;
import static com.example.service.user.utils.DataFaker.fakeUserDto;
import static com.example.service.user.utils.DataFaker.fakeUserIdAsInt;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FindUserEndpointAdapterTest {

    @InjectMocks
    private FindUserEndpointAdapter findUserEndpointAdapter;

    @Mock
    private FindAllUsersUseCase findAllUsersUseCase;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Test
    public void shouldReturnDtoObject_whenGettingByUserId() {
        int userIdInt = fakeUserIdAsInt();
        UserId userId = UserId.of(userIdInt);
        User user = fakeUser();
        UserDto userDto = fakeUserDto();

        Mockito.when(findUserByIdUseCase.findById(userId)).thenReturn(user);
        Mockito.when(userDtoMapper.toDto(user)).thenReturn(userDto);

        UserDto fetchUserById = findUserEndpointAdapter.fetchUserById(userIdInt);
        assertThat(fetchUserById).isEqualTo(userDto);
    }

    @Test
    public void shouldReturnEmpty_whenThereIsNoUserPersisted() {
        Mockito.when(findAllUsersUseCase.fetchAllPersisted()).thenReturn(List.of());

        Collection<UserDto> userDtos = findUserEndpointAdapter.fetchAllUsers();
        assertThat(userDtos).isEmpty();
    }

    @Test
    public void shouldListOfUsers_whenUsersArePersisted() {
        User user1 = fakeUser();
        User user2 = fakeUser();
        UserDto userDto1 = fakeUserDto();
        UserDto userDto2 = fakeUserDto();

        Mockito.when(findAllUsersUseCase.fetchAllPersisted()).thenReturn(List.of(user1, user2));
        Mockito.when(userDtoMapper.toDto(user1)).thenReturn(userDto1);
        Mockito.when(userDtoMapper.toDto(user2)).thenReturn(userDto2);

        Collection<UserDto> userDtos = findUserEndpointAdapter.fetchAllUsers();
        assertThat(userDtos)
                .hasSize(2)
                .containsExactlyInAnyOrder(userDto1, userDto2);
    }

}