package com.example.hexagonal.arch.service.users.adapter.api;

import com.example.hexagonal.arch.service.users.adapter.api.model.SaveUserBodyDto;
import com.example.hexagonal.arch.service.users.adapter.api.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final SearchUserEndpointAdapter searchUserEndpointAdapter;

    UserController(ChangeUserEndpointAdapter changeUserEndpointAdapter,
                   SearchUserEndpointAdapter searchUserEndpointAdapter) {
        this.changeUserEndpointAdapter = changeUserEndpointAdapter;
        this.searchUserEndpointAdapter = searchUserEndpointAdapter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> saveUser(@RequestBody @Valid SaveUserBodyDto saveUserBodyDto) {
        return changeUserEndpointAdapter.saveUser(saveUserBodyDto)
                .mono();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserDto> fetchAllUsers() {
        return searchUserEndpointAdapter.fetchAllUsers()
                .flux();
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> fetchUserById(@PathVariable("user_id") Integer userId) {
        return searchUserEndpointAdapter.fetchUserById(userId)
                .mono();
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUserById(@PathVariable("user_id") Integer userId) {
        return changeUserEndpointAdapter.deleteUser(userId)
                .mono();
    }
}
