package com.example.jiraproject.comment.model;

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

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Comment.CommentEntity.TABLE_NAME)
public class Comment extends BaseEntity {

    @Column(name = CommentEntity.DESCRIPTION, nullable = false)
    @Size(max = 300, message = "{comment.description.size}")
    @NotBlank(message = "{comment.description.not-blank}")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = JoinTableUtil.COMMENT_REFERENCE_USER, nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = JoinTableUtil.COMMENT_REFERENCE_TASK, nullable = false)
    private Task task;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = JoinTableUtil.COMMENT_REFERENCE_COMMENT)
    private Comment responseTo;

    @OneToOne(mappedBy = JoinTableUtil.COMMENT_REFERENCE_COMMENT,
    cascade = CascadeType.ALL) //delete this comment will delete all comments responding to this comment
    private Comment cmt;

    @UtilityClass
    static class CommentEntity {
        public static final String TABLE_NAME = "J_COMMENT";
        public static final String DESCRIPTION = "J_DESCRIPTION";
    }
}
