package com.example.service.user.adapter.outbound.persistence;

import com.example.service.user.adapter.outbound.persistence.model.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.example.service.user.utils.DataFaker.fakeUserDataBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnTrueWhenFirstNameLastNameUserIsPresent() {
        UserData userData = fakeUserDataBuilder().id(null).build();
        userRepository.save(userData);

        String firstName = userData.getFirstName();
        String lastName = userData.getLastName();
        boolean userExists = userRepository.existsByFirstNameAndLastName(firstName, lastName);
        assertThat(userExists).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenFirstNameLastNameUserIsPresent() {
        UserData userData = fakeUserDataBuilder().id(null).build();
        userRepository.save(userData);

        String firstName = userData.getFirstName() + "a";
        String lastName = userData.getLastName();
        boolean userExists = userRepository.existsByFirstNameAndLastName(firstName, lastName);
        assertThat(userExists).isFalse();
    }
}