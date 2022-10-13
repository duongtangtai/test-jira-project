package com.example.jiraproject.user.service;

import com.example.jiraproject.common.service.GenericService;
import com.example.jiraproject.common.util.MessageUtil;
import com.example.jiraproject.role.service.RoleService;
import com.example.jiraproject.user.dto.UserDto;
import com.example.jiraproject.user.dto.UserWithRoleDto;
import com.example.jiraproject.user.model.User;
import com.example.jiraproject.user.repository.UserRepository;
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

public interface UserService extends GenericService<User, UserDto, UUID> {
    User findUserById(UUID id);
    List<UserWithRoleDto> findAllWithRoles();
    UserWithRoleDto addRoles(UUID userId, Set<UUID> roleIds);
    UserWithRoleDto removeRoles(UUID userId, Set<UUID> roleIds);
}

@Service
@Transactional
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;
    private final RoleService roleService;
    private final MessageSource messageSource;
    private static final String UUID_NOT_FOUND = "user.id.not-found";

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    public User findUserById(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
    }

    @Override
    public List<UserWithRoleDto> findAllWithRoles() {
        return repository.findAllWithRoles().stream()
                .map(model -> mapper.map(model, UserWithRoleDto.class))
                .toList();
    }

    @Override
    public UserWithRoleDto addRoles(UUID userId, Set<UUID> roleIds) {
        User user = repository.findById(userId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        roleService.findAllByIds(roleIds).forEach(user::addRole);
        return mapper.map(user, UserWithRoleDto.class);
    }

    @Override
    public UserWithRoleDto removeRoles(UUID userId, Set<UUID> roleIds) {
        User user = repository.findById(userId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        roleService.findAllByIds(roleIds).forEach(user::removeRole);
        return mapper.map(user, UserWithRoleDto.class);
    }
}
