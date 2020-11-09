package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserData, Integer> {

    Flux<UserData> findByFirstNameAndLastName(String firstName, String lastName);
}
