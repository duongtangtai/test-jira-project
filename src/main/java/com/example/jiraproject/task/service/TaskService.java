package com.example.jiraproject.task.service;

import com.example.jiraproject.common.service.GenericService;
import com.example.jiraproject.task.dto.TaskDto;
import com.example.jiraproject.task.model.Task;
import com.example.jiraproject.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TaskService extends GenericService<Task, TaskDto, UUID> {

}
@Service
@Transactional
@RequiredArgsConstructor
class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final ModelMapper mapper;
    private final MessageSource messageSource;
    private static final String UUID_NOT_FOUND = "task.id.not-found";

    @Override
    public JpaRepository<Task, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }
}
