package com.example.jiraproject.user.dto;

import com.example.jiraproject.role.dto.RoleDto;
import com.example.jiraproject.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserWithRoleDto {
    private UUID id;
    private String username;
    private String password;
    private String fullName;
    private String displayedName;
    private String avatar;
    private String email;
    private String facebookUrl;
    private String occupation;
    private String department;
    private String hobbies;
    private User.AccountStatus accountStatus;
    private Set<RoleDto> roles;
}
