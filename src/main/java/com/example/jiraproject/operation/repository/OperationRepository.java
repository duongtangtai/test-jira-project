package com.example.jiraproject.operation.repository;

import com.example.jiraproject.operation.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {
    Optional<Operation> findByNameAndType(String name, Operation.Type type);
}
