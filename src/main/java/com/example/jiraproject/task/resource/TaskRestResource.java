package com.example.jiraproject.task.resource;

import com.example.jiraproject.common.dto.ResponseDto;
import com.example.jiraproject.common.util.MessageUtil;
import com.example.jiraproject.common.util.ResponseUtil;
import com.example.jiraproject.common.validation.annotation.UUIDConstraint;
import com.example.jiraproject.common.validation.group.SaveInfo;
import com.example.jiraproject.common.validation.group.UpdateInfo;
import com.example.jiraproject.task.dto.TaskDto;
import com.example.jiraproject.task.dto.TaskWithProjectAndUserDto;
import com.example.jiraproject.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Validated
public class TaskRestResource {
    private final TaskService service;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<ResponseDto> findAll() {
        return ResponseUtil.get(service.findAll(TaskWithProjectAndUserDto.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(@PathVariable("id") @UUIDConstraint String id) {
        return ResponseUtil.get(service.findById(TaskWithProjectAndUserDto.class, UUID.fromString(id)), HttpStatus.OK);
    }

    @GetMapping("/with-paging")
    public ResponseEntity<ResponseDto> findAllWithPaging(@RequestParam("size") int size,
                                                         @RequestParam("pageIndex") int pageIndex) {
        return ResponseUtil.get(service.findAllWithPaging(TaskWithProjectAndUserDto.class, size, pageIndex), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> save(@RequestBody @Validated(SaveInfo.class) TaskDto taskDto,
                                            @RequestParam("projectId") @UUIDConstraint String projectId,
                                            @RequestParam("reporterId") @UUIDConstraint String reporterId) {
        return ResponseUtil.get(service.createTask(taskDto, UUID.fromString(projectId), UUID.fromString(reporterId)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> update(@RequestBody @Validated(UpdateInfo.class) TaskDto taskDto,
                                              @RequestParam("reporterId") @UUIDConstraint String reporterId) {
        return ResponseUtil.get(service.updateTask(taskDto, UUID.fromString(reporterId)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable("id") @UUIDConstraint String id) {
        service.deleteById(UUID.fromString(id));
        return ResponseUtil.get(MessageUtil.getMessage(messageSource, "task.deleted"), HttpStatus.OK);
    }
}
