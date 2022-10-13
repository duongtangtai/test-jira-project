package com.example.jiraproject.task.model;

import com.example.jiraproject.common.model.BaseEntity;
import com.example.jiraproject.common.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Task.TaskEntity.TABLE_NAME)
public class Task extends BaseEntity {

    @Column(name = TaskEntity.NAME, unique = true, nullable = false)
    @Size(min = 5, max = 50, message = "{task.name.size}")
    @NotBlank(message = "{task.name.not-blank}")
    private String name;

    @Column(name = TaskEntity.DESCRIPTION, nullable = false)
    @Size(min = 5, max = 300, message = "{task.description.size}")
    @NotBlank(message = "{task.description.not-blank}")
    private String description;

    @Column(name = TaskEntity.STARTED_DATE_EXPECTED, nullable = false)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.DATE_FORMAT)
    private LocalDate startDateExpected;

    @Column(name = TaskEntity.END_DATE_EXPECTED, nullable = false)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.DATE_FORMAT)
    private LocalDate endDateExpected;

    @Column(name = TaskEntity.STARTED_DATE_IN_FACT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.DATE_FORMAT)
    private LocalDate startDateInFact;

    @Column(name = TaskEntity.END_DATE_IN_FACT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.DATE_FORMAT)
    private LocalDate endDateInFact;

    @Column(name = TaskEntity.TASK_STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        UNASSIGNED,
        STARTED,
        COMPLETED
    }

    @UtilityClass
    static class TaskEntity {
        public static final String TABLE_NAME = "J_TASK";
        public static final String NAME = "J_NAME";
        public static final String DESCRIPTION = "J_DESCRIPTION";
        public static final String ESTIMATED_TIME = "J_ESTIMATED_TIME";
        public static final String STARTED_DATE_EXPECTED = "J_STARTED_DATE_EXPECTED";
        public static final String END_DATE_EXPECTED = "J_END_DATE_EXPECTED";
        public static final String STARTED_DATE_IN_FACT = "J_STARTED_DATE_IN_FACT";
        public static final String END_DATE_IN_FACT = "J_END_DATE_IN_FACT";
        public static final String TASK_STATUS = "J_TASK_STATUS";
    }
}
