package com.example.hexagonal.arch.service.users.adapter.persistence;

import com.example.hexagonal.arch.service.common.annotation.Repository;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserData, Integer> {

    Flux<UserData> findByFirstNameAndLastName(String firstName, String lastName);
}
