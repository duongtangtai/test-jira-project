package com.example.jiraproject.user.model;

import com.example.jiraproject.comment.model.Comment;
import com.example.jiraproject.common.model.BaseEntity;
import com.example.jiraproject.common.util.JoinTableUtil;
import com.example.jiraproject.project.model.Project;
import com.example.jiraproject.role.model.Role;
import com.example.jiraproject.task.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

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
@Table(name = User.UserEntity.TABLE_NAME)
@Slf4j
public class User extends BaseEntity {

    @Column(name = UserEntity.USERNAME, nullable = false)
    @Size(min = 5, max = 25, message = "{user.username.size}")
    @NotBlank(message = "{user.username.not-blank}")
    private String username;

    @Column(name = UserEntity.PASSWORD, nullable = false)
    @Size(min = 5, max = 25, message = "{user.password.size}")
    @NotBlank(message = "{user.password.not-blank}")
    private String password;

    @Column(name = UserEntity.FULL_NAME, nullable = false)
    @Size(min = 5, max = 25, message = "{user.fullName.size}")
    @NotBlank(message = "{user.fullName.not-blank}")
    private String fullName;

    @Column(name = UserEntity.DISPLAYED_NAME, nullable = false)
    @Size(min = 5, max = 25, message = "{user.displayedName.size}")
    @NotBlank(message = "{user.displayedName.not-blank}")
    private String displayedName;

    @Column(name = UserEntity.AVATAR)
    @Size(max = 200, message = "{user.avatar.size}")
    private String avatar;

    @Column(name = UserEntity.EMAIL, nullable = false)
    @Size(min = 5, max = 25, message = "{user.email.size}")
    @NotBlank(message = "{user.email.not-blank}")
    private String email;

    @Column(name = UserEntity.FACEBOOK_URL)
    @Size(min = 5, max = 200, message = "{user.facebookUrl.size}")
    private String facebookUrl;

    @Column(name = UserEntity.OCCUPATION)
    @Size(min = 5, max = 200, message = "{user.occupation.size}")
    private String occupation;

    @Column(name = UserEntity.DEPARTMENT)
    @Size(min = 5, max = 200, message = "{user.department.size}")
    private String department;

    @Column(name = UserEntity.HOBBIES)
    @Size(min = 5, max = 200, message = "{user.hobbies.size}")
    private String hobbies;

    @Column(name = UserEntity.ACCOUNT_STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = JoinTableUtil.USER_JOIN_WITH_ROLE,
            joinColumns = @JoinColumn(name = JoinTableUtil.USER_ID),
            inverseJoinColumns = @JoinColumn(name = JoinTableUtil.ROLE_ID)
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = JoinTableUtil.PROJECT_CREATOR_REFERENCE_USER,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Project> creators;

    @OneToMany(mappedBy = JoinTableUtil.PROJECT_LEADER_REFERENCE_USER,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Project> leaders;

    @OneToMany(mappedBy = JoinTableUtil.TASK_REFERENCE_USER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Task> reporters;

    @OneToMany(mappedBy = JoinTableUtil.COMMENT_REFERENCE_USER,
    cascade = CascadeType.ALL) //delete user will delete all comments
    private Set<Comment> comments;

    public void addRole(Role role) {
        this.getRoles().add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }

    //------------------ENTITY LIFE CYCLES-------------------
    @PreRemove
    private void beforeRemoveUser() { //set null to relative projects and tasks
        log.info("PreRemoveUser - Trying to set nulls to relative projects and tasks");
        getCreators().forEach(project -> project.setCreator(null));
        getLeaders().forEach(project -> project.setLeader(null));
        getReporters().forEach(task -> task.setReporter(null));
    }


    @Override
    public int hashCode() {
        return (getClass() + username).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User user = (User) obj;
        return user.getUsername().equals(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", displayedName='" + displayedName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", facebookUrl='" + facebookUrl + '\'' +
                ", occupation='" + occupation + '\'' +
                ", department='" + department + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }

    public enum AccountStatus {
        ACTIVE,
        TEMPORARILY_BLOCKED,
        PERMANENTLY_BLOCKED
    }

    @UtilityClass
    static class UserEntity {
        public static final String TABLE_NAME = "J_USER";
        public static final String USERNAME = "J_USERNAME";
        public static final String PASSWORD = "J_PASSWORD";
        public static final String FULL_NAME = "J_FULL_NAME";
        public static final String DISPLAYED_NAME = "J_DISPLAYED_NAME";
        public static final String AVATAR = "J_AVATAR";
        public static final String EMAIL = "J_EMAIL";
        public static final String FACEBOOK_URL = "J_FACEBOOK_URL";
        public static final String OCCUPATION = "J_OCCUPATION";
        public static final String DEPARTMENT = "J_DEPARTMENT";
        public static final String HOBBIES = "J_HOBBIES";
        public static final String ACCOUNT_STATUS = "J_ACCOUNT_STATUS";
    }
}
