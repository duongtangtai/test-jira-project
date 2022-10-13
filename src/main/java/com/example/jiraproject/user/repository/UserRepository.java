package com.example.jiraproject.user.repository;

import com.example.jiraproject.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query(value = "select u from User u left join fetch u.roles")
    Set<User> findAllWithRoles();
}
