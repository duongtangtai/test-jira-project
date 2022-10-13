package com.example.jiraproject.project.model;

import com.example.jiraproject.common.model.BaseEntity;
import com.example.jiraproject.common.util.JoinTableUtil;
import com.example.jiraproject.task.model.Task;
import com.example.jiraproject.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Project.ProjectEntity.TABLE_NAME)
public class Project extends BaseEntity{

    @Column(name = ProjectEntity.NAME, unique = true, nullable = false)
    @Size(min = 5, max = 50, message = "{project.name.size}")
    @NotBlank(message = "{project.name.not-blank}")
    private String name;

    @Column(name = ProjectEntity.DESCRIPTION, nullable = false)
    @Size(min = 5, max = 500, message = "{project.description.size}")
    @NotBlank(message = "{project.description.not-blank}")
    private String description;

    @Column(name = ProjectEntity.SYMBOL)
    @Size(min = 5, max = 100, message = "{project.symbol.size}")
    private String symbol;

    @ManyToOne
    @JoinColumn(name = JoinTableUtil.PROJECT_CREATOR_REFERENCE_USER)
    private User creator;

    @ManyToOne
    @JoinColumn(name = JoinTableUtil.PROJECT_LEADER_REFERENCE_USER)
    private User leader;

    @OneToMany(mappedBy = JoinTableUtil.TASK_REFERENCE_PROJECT,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Task> tasks;

    public void addCreator(User user){
        this.setCreator(user);
        user.getCreators().add(this);
    }

    public void removeCreator() {
        User user = this.getCreator();
        this.setCreator(null);
        user.getCreators().remove(this);
    }

    public void addLeader(User user){
        this.setLeader(user);
        user.getLeaders().add(this);
    }

    public void removeLeader() {
        User user = this.getLeader();
        this.setLeader(null);
        user.getLeaders().remove(this);
    }

    @UtilityClass
    static class ProjectEntity {
        public static final String TABLE_NAME = "J_PROJECT";
        public static final String NAME = "J_NAME";
        public static final String DESCRIPTION = "J_DESCRIPTION";
        public static final String SYMBOL = "J_SYMBOL";
    }
}
