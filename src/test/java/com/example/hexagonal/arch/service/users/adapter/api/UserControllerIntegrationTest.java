package com.example.hexagonal.arch.service.users.adapter.api;

import com.example.hexagonal.arch.service.users.adapter.api.model.SaveUserBodyDto;
import com.example.hexagonal.arch.service.users.adapter.persistence.UserRepository;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import com.example.hexagonal.arch.service.users.domain.model.User;
import com.example.hexagonal.arch.service.users.domain.model.UserFunctions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static com.example.hexagonal.arch.service.common.DataFaker.fakeUser;
import static com.example.hexagonal.arch.service.common.DataFaker.fakeUserData;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userFirstName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userLastName;
import static com.example.hexagonal.arch.service.users.domain.model.UserFunctions.userPhoneNumber;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionalOperator transactionalOperator;

    @BeforeEach
    public void setUp() {
        transactionalOperator.transactional(userRepository.deleteAll()).block();
    }

    @Test
    public void shouldCreateNewUser_OnUsersPostRequest() {
        User user = fakeUser();
        SaveUserBodyDto saveUserBodyDto = SaveUserBodyDto.of(
                userFirstName.apply(user),
                userLastName.apply(user),
                userPhoneNumber.apply(user));

        webClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(saveUserBodyDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("first_name").isEqualTo(userFirstName.apply(user))
                .jsonPath("last_name").isEqualTo(userLastName.apply(user))
                .jsonPath("phone").isEqualTo(userPhoneNumber.apply(user));
    }

    @Test
    public void shouldRetrieveUserById_whenPrePopulatedOnTheDatabase() {
        UserData userData = fakeUserData().toBuilder().id(null).build();
        UserData userDataPersisted = transactionalOperator.transactional(userRepository.save(userData))
                .block();

        webClient.get()
                .uri("/users/" + userDataPersisted.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("first_name").isEqualTo(userData.getFirstName())
                .jsonPath("last_name").isEqualTo(userData.getLastName())
                .jsonPath("phone").isEqualTo(userData.getPhone());
    }

    @Test
    public void shouldRetrieveAllUsers_whenPrePopulatedOnTheDatabase() {
        UserData userData1 = fakeUserData().toBuilder().id(null).build();
        UserData userData2 = fakeUserData().toBuilder().id(null).build();
        transactionalOperator.transactional(userRepository.saveAll(List.of(userData1, userData2)))
                .blockLast();

        webClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].first_name").isEqualTo(userData1.getFirstName())
                .jsonPath("$[1].first_name").isEqualTo(userData2.getFirstName())
                .jsonPath("$[0].last_name").isEqualTo(userData1.getLastName())
                .jsonPath("$[1].last_name").isEqualTo(userData2.getLastName())
                .jsonPath("$[0].phone").isEqualTo(userData1.getPhone())
                .jsonPath("$[1].phone").isEqualTo(userData2.getPhone());
    }

    @Test
    public void shouldHardDeleteUser_whenPrePopulatedOnTheDatabase() {
        UserData userData = fakeUserData().toBuilder().id(null).build();
        UserData userDataPersisted = transactionalOperator.transactional(userRepository.save(userData))
                .block();

        webClient.delete()
                .uri("/users/" + userDataPersisted.getId())
                .exchange()
                .expectStatus().isNoContent();

        Boolean userExists = userRepository.existsById(userDataPersisted.getId()).block();
        assertThat(userExists).isFalse();
    }
}