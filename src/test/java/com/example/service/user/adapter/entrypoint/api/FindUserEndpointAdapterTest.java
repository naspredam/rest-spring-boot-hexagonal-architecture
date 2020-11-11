package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.application.usecase.FindAllUsersUseCase;
import com.example.service.user.application.usecase.FindUserByIdUseCase;
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
        UnitReactive<User> userUnitReactive = UnitReactive.of(Mono.just(user));
        UserDto userDto = fakeUserDto();

        Mockito.when(findUserByIdUseCase.findById(userId)).thenReturn(userUnitReactive);
        Mockito.when(userDtoMapper.toDto(user)).thenReturn(userDto);

        UnitReactive<UserDto> userDtoUnitReactive = findUserEndpointAdapter.fetchUserById(userIdInt);
        assertThat(userDtoUnitReactive.mono().block()).isEqualTo(userDto);
    }

    @Test
    public void shouldReturnEmpty_whenThereIsNoUserPersisted() {
        CollectionReactive<User> userCollectionReactive = CollectionReactive.of(Flux.empty());

        Mockito.when(findAllUsersUseCase.fetchAllPersisted()).thenReturn(userCollectionReactive);

        CollectionReactive<UserDto> userDtoCollectionReactive = findUserEndpointAdapter.fetchAllUsers();
        List<UserDto> userDtos = userDtoCollectionReactive.flux().collectList().block();
        assertThat(userDtos).isEmpty();
    }

    @Test
    public void shouldListOfUsers_whenUsersArePersisted() {
        User user1 = fakeUser();
        User user2 = fakeUser();
        CollectionReactive<User> userCollectionReactive = CollectionReactive.of(Flux.just(user1, user2));

        UserDto userDto1 = fakeUserDto();
        UserDto userDto2 = fakeUserDto();

        Mockito.when(findAllUsersUseCase.fetchAllPersisted()).thenReturn(userCollectionReactive);
        Mockito.when(userDtoMapper.toDto(user1)).thenReturn(userDto1);
        Mockito.when(userDtoMapper.toDto(user2)).thenReturn(userDto2);

        CollectionReactive<UserDto> userDtoCollectionReactive = findUserEndpointAdapter.fetchAllUsers();
        List<UserDto> userDtos = userDtoCollectionReactive.flux().collectList().block();
        assertThat(userDtos).hasSize(2).containsExactlyInAnyOrder(userDto1, userDto2);
    }

}