package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.persistence.UserRepository;
import com.example.service.user.adapter.persistence.model.UserData;
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

import static com.example.service.user.utils.DataFaker.fakeSaveUserBodyDto;
import static com.example.service.user.utils.DataFaker.fakeUserData;
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
        SaveUserBodyDto saveUserBodyDto = fakeSaveUserBodyDto();

        webClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(saveUserBodyDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("first_name").isEqualTo(saveUserBodyDto.getFirstName())
                .jsonPath("last_name").isEqualTo(saveUserBodyDto.getLastName())
                .jsonPath("phone").isEqualTo(saveUserBodyDto.getPhone());
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