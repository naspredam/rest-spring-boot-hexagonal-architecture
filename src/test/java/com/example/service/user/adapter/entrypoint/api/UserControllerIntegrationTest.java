package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.persistence.UserRepository;
import com.example.service.user.adapter.persistence.model.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.service.user.utils.DataFaker.fakeSaveUserBodyDto;
import static com.example.service.user.utils.DataFaker.fakeUserData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateNewUser_OnUsersPostRequest() throws Exception {
        SaveUserBodyDto saveUserBodyDto = fakeSaveUserBodyDto();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(saveUserBodyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("first_name", is(saveUserBodyDto.getFirstName())))
                .andExpect(jsonPath("last_name", is(saveUserBodyDto.getLastName())))
                .andExpect(jsonPath("phone", is(saveUserBodyDto.getPhone())));
    }

    @Test
    public void shouldRetrieveUserById_whenPrePopulatedOnTheDatabase() throws Exception {
        UserData userData = fakeUserData().toBuilder().id(null).build();
        UserData userDataPersisted = userRepository.save(userData);

        mockMvc.perform(get("/users/" + userDataPersisted.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("first_name", is(userData.getFirstName())))
                .andExpect(jsonPath("last_name", is(userData.getLastName())))
                .andExpect(jsonPath("phone", is(userData.getPhone())));
    }

    @Test
    public void shouldRetrieveAllUsers_whenPrePopulatedOnTheDatabase() throws Exception {
        UserData userData1 = fakeUserData().toBuilder().id(null).build();
        UserData userData2 = fakeUserData().toBuilder().id(null).build();
        userRepository.saveAll(List.of(userData1, userData2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].first_name", is(userData1.getFirstName())))
                .andExpect(jsonPath("$[1].first_name", is(userData2.getFirstName())))
                .andExpect(jsonPath("$[0].last_name", is(userData1.getLastName())))
                .andExpect(jsonPath("$[1].last_name", is(userData2.getLastName())))
                .andExpect(jsonPath("$[0].phone", is(userData1.getPhone())))
                .andExpect(jsonPath("$[1].phone", is(userData2.getPhone())));
    }

    @Test
    public void shouldHardDeleteUser_whenPrePopulatedOnTheDatabase() throws Exception {
        UserData userData = fakeUserData().toBuilder().id(null).build();
        UserData userDataPersisted = userRepository.save(userData);

        mockMvc.perform(delete("/users/" + userDataPersisted.getId()))
                .andExpect(status().isNoContent());

        Boolean userExists = userRepository.existsById(userDataPersisted.getId());
        assertThat(userExists).isFalse();
    }

}