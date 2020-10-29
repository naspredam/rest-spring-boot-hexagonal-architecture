package com.example.hexagonal.arch.service.users.adapter.persistence;

import com.example.hexagonal.arch.service.common.annotation.Repository;
import com.example.hexagonal.arch.service.users.adapter.persistence.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    Collection<UserData> findByFirstNameAndLastName(String firstName, String lastName);
}
