package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import com.example.service.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.service.user.domain.UserFunctions.userFirstName;
import static com.example.service.user.domain.UserFunctions.userIdAsInt;
import static com.example.service.user.domain.UserFunctions.userLastName;
import static com.example.service.user.domain.UserFunctions.userPhoneNumber;
import static com.example.service.user.utils.DataFaker.fakeSaveUserBodyDto;
import static com.example.service.user.utils.DataFaker.fakeUser;
import static com.example.service.user.utils.DataFaker.fakeUserIdAsInt;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserDtoMapperTest {

    @InjectMocks
    private UserDtoMapper userDtoMapper;

    @Test
    public void shouldMapToDtoFromDomain() {
        User user = fakeUser();
        UserDto userDto = userDtoMapper.toDto(user);

        assertThat(userDto.getFirstName()).isEqualTo(userFirstName.apply(user));
        assertThat(userDto.getLastName()).isEqualTo(userLastName.apply(user));
        assertThat(userDto.getPhone()).isEqualTo(userPhoneNumber.apply(user));
    }

    @Test
    public void shouldMapToDomainFromDtoWithoutUserId() {
        SaveUserBodyDto saveUserBodyDto = fakeSaveUserBodyDto();
        User user = userDtoMapper.toDomainFromSaveBody(saveUserBodyDto);

        assertThat(userIdAsInt.apply(user)).isNull();
        assertThat(userFirstName.apply(user)).isEqualTo(saveUserBodyDto.getFirstName());
        assertThat(userLastName.apply(user)).isEqualTo(saveUserBodyDto.getLastName());
        assertThat(userPhoneNumber.apply(user)).isEqualTo(saveUserBodyDto.getPhone());
    }

    @Test
    public void shouldMapToDomainFromDtoWithUserId() {
        Integer userId = fakeUserIdAsInt();
        SaveUserBodyDto saveUserBodyDto = fakeSaveUserBodyDto();

        User user = userDtoMapper.toDomainFromSaveBody(userId, saveUserBodyDto);

        assertThat(userIdAsInt.apply(user)).isEqualTo(userId);
        assertThat(userFirstName.apply(user)).isEqualTo(saveUserBodyDto.getFirstName());
        assertThat(userLastName.apply(user)).isEqualTo(saveUserBodyDto.getLastName());
        assertThat(userPhoneNumber.apply(user)).isEqualTo(saveUserBodyDto.getPhone());
    }
}