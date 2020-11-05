package com.example.service.user.adapter.persistence;

import com.example.service.user.adapter.persistence.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    Collection<UserData> findByFirstNameAndLastName(String firstName, String lastName);
}
