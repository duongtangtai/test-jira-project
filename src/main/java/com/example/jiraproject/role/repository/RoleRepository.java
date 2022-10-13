package com.example.jiraproject.role.repository;

import com.example.jiraproject.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByNameOrCode(String name, String code);

    @Query(value = "select r from Role r left join fetch r.operations")
    Set<Role> findAllRolesWithOperations();
}
