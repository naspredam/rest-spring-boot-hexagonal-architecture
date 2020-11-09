package com.example.service.user.adapter.entrypoint.api;

import com.example.service.user.adapter.entrypoint.api.model.SaveUserBodyDto;
import com.example.service.user.adapter.entrypoint.api.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
class UserController {

    private final ChangeUserEndpointAdapter changeUserEndpointAdapter;

    private final FindUserEndpointAdapter findUserEndpointAdapter;

    UserController(ChangeUserEndpointAdapter changeUserEndpointAdapter,
                   FindUserEndpointAdapter findUserEndpointAdapter) {
        this.changeUserEndpointAdapter = changeUserEndpointAdapter;
        this.findUserEndpointAdapter = findUserEndpointAdapter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> saveUser(@RequestBody @Valid SaveUserBodyDto saveUserBodyDto) {
        return changeUserEndpointAdapter.saveUser(saveUserBodyDto).mono();
    }

    @PutMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> updateUser(@PathVariable("user_id") int userId,
                              @RequestBody @Valid SaveUserBodyDto saveUserBodyDto) {
        return changeUserEndpointAdapter.updateUser(userId, saveUserBodyDto).mono();
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> fetchUserById(@PathVariable("user_id") Integer userId) {
        return findUserEndpointAdapter.fetchUserById(userId).mono();
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUserById(@PathVariable("user_id") Integer userId) {
        return changeUserEndpointAdapter.deleteUser(userId).mono();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserDto> fetchAllUsers() {
        return findUserEndpointAdapter.fetchAllUsers().flux();
    }

}
