package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static com.example.service.user.utils.DataFaker.fakeUserDataBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnTrueWhenFirstNameLastNameUserIsPresent() {
        UserData userData = fakeUserDataBuilder().id(null).build();
        userRepository.save(userData);

        String firstName = userData.getFirstName();
        String lastName = userData.getLastName();
        Collection<UserData> found = userRepository.findByFirstNameAndLastName(firstName, lastName);
        assertThat(found.isEmpty()).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenFirstNameLastNameUserIsPresent() {
        UserData userData = fakeUserDataBuilder().id(null).build();
        userRepository.save(userData);

        String firstName = userData.getFirstName() + "a";
        String lastName = userData.getLastName();

        Collection<UserData> found = userRepository.findByFirstNameAndLastName(firstName, lastName);
        assertThat(found.isEmpty()).isTrue();
    }
}