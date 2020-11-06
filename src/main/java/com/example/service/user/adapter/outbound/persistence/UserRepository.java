package com.example.service.user.adapter.outbound.persistence;

import com.example.service.user.adapter.outbound.persistence.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
