package com.example.jiraproject.role.service;

import com.example.jiraproject.common.service.GenericService;
import com.example.jiraproject.common.util.MessageUtil;
import com.example.jiraproject.operation.service.OperationService;
import com.example.jiraproject.role.dto.RoleDto;
import com.example.jiraproject.role.dto.RoleWithOperationDto;
import com.example.jiraproject.role.model.Role;
import com.example.jiraproject.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleService extends GenericService<Role, RoleDto, UUID> {
    RoleWithOperationDto addOperations(UUID roleId, Set<UUID> operationIds);
    RoleWithOperationDto removeOperations(UUID roleId, Set<UUID> operationIds);
    List<RoleWithOperationDto> findAllWithOperations();
    List<Role> findAllByIds(Set<UUID> roleIds);
}
@Service
@Transactional
@RequiredArgsConstructor
class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final ModelMapper mapper;
    private final OperationService operationService;
    private final MessageSource messageSource;
    private static final String UUID_NOT_FOUND = "role.id.not-found";

    @Override
    public JpaRepository<Role, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    public List<RoleWithOperationDto> findAllWithOperations() {
        return repository.findAllRolesWithOperations().stream()
                .map(role -> mapper.map(role, RoleWithOperationDto.class))
                .toList();
    }

    @Override
    public List<Role> findAllByIds(Set<UUID> roleIds) {
        return repository.findAllById(roleIds);
    }

    @Override
    public RoleWithOperationDto addOperations(UUID roleId, Set<UUID> operationIds) {
        Role role = repository.findById(roleId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        operationService.findAllByIds(operationIds).forEach(role::addOperation);
        return mapper.map(role, RoleWithOperationDto.class);
    }

    @Override
    public RoleWithOperationDto removeOperations(UUID roleId, Set<UUID> operationIds) {
        Role role = repository.findById(roleId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        operationService.findAllByIds(operationIds).forEach(role::removeOperation);
        return mapper.map(role, RoleWithOperationDto.class);
    }
}
