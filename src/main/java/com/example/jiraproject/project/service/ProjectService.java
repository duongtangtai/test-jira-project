package com.example.jiraproject.project.service;

import com.example.jiraproject.common.service.GenericService;
import com.example.jiraproject.common.util.MessageUtil;
import com.example.jiraproject.project.dto.ProjectDto;
import com.example.jiraproject.project.dto.ProjectWithUserDto;
import com.example.jiraproject.project.model.Project;
import com.example.jiraproject.project.repository.ProjectRepository;
import com.example.jiraproject.user.model.User;
import com.example.jiraproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

public interface ProjectService extends GenericService<Project, ProjectDto, UUID> {
    Project findProjectById(UUID projectId);
    List<ProjectWithUserDto> findAllWithCreatorAndLeader();
    ProjectWithUserDto addCreator(UUID projectId, UUID userId);
    ProjectWithUserDto removeCreator(UUID projectId);
    ProjectWithUserDto addLeader(UUID projectId, UUID userId);
    ProjectWithUserDto removeLeader(UUID projectId);
}
@Service
@Transactional
@RequiredArgsConstructor
class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository repository;
    private final ModelMapper mapper;
    private final MessageSource messageSource;
    private final UserService userService;
    private static final String UUID_NOT_FOUND = "project.id.not-found";

    @Override
    public JpaRepository<Project, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    public Project findProjectById(UUID projectId) {
        return repository.findById(projectId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
    }

    @Override
    public List<ProjectWithUserDto> findAllWithCreatorAndLeader() {
        return repository.findAllWithCreatorAndLeader().stream()
                .map(model -> mapper.map(model, ProjectWithUserDto.class)).toList();
    }

    @Override
    public ProjectWithUserDto addCreator(UUID projectId, UUID userId) {
        Project project = repository.findById(projectId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        User user = userService.findUserById(userId);
        project.addCreator(user);
        return mapper.map(project, ProjectWithUserDto.class);
    }

    @Override
    public ProjectWithUserDto removeCreator(UUID projectId) {
        Project project = repository.findById(projectId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        project.removeCreator();
        return mapper.map(project, ProjectWithUserDto.class);
    }

    @Override
    public ProjectWithUserDto addLeader(UUID projectId, UUID userId) {
        Project project = repository.findById(projectId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        User user = userService.findUserById(userId);
        project.addLeader(user);
        return mapper.map(project, ProjectWithUserDto.class);
    }

    @Override
    public ProjectWithUserDto removeLeader(UUID projectId) {
        Project project = repository.findById(projectId)
                .orElseThrow(() ->
                        new ValidationException(MessageUtil.getMessage(messageSource, UUID_NOT_FOUND)));
        project.removeLeader();
        return mapper.map(project, ProjectWithUserDto.class);
    }
}
