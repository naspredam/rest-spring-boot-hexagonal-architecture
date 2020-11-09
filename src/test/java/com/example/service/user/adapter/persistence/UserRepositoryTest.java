package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;

import static com.example.service.user.utils.DataFaker.fakeUserDataBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionalOperator transactionalOperator;

    @BeforeEach
    public void setUp() {
        transactionalOperator.transactional(userRepository.deleteAll()).block();
    }

    @Test
    public void shouldReturnTrueWhenFirstNameLastNameUserIsPresent() {
        UserData userData = fakeUserDataBuilder().id(null).build();
        transactionalOperator.transactional(userRepository.save(userData)).block();

        String firstName = userData.getFirstName();
        String lastName = userData.getLastName();
        Flux<UserData> found = userRepository.findByFirstNameAndLastName(firstName, lastName);
        assertThat(found.hasElements().block()).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenFirstNameLastNameUserIsPresent() {
        UserData userData = fakeUserDataBuilder().id(null).build();
        transactionalOperator.transactional(userRepository.save(userData)).block();

        String firstName = userData.getFirstName() + "a";
        String lastName = userData.getLastName();

        Flux<UserData> found = userRepository.findByFirstNameAndLastName(firstName, lastName);
        assertThat(found.hasElements().block()).isFalse();
    }
}