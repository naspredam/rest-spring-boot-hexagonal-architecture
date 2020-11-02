package com.example.service.user.adapter.persistence;

import com.example.service.common.annotation.Repository;
import com.example.service.user.adapter.persistence.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    Collection<UserData> findByFirstNameAndLastName(String firstName, String lastName);
}
