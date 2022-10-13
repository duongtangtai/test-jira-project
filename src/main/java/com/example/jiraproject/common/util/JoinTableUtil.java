package com.example.jiraproject.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JoinTableUtil {
    //COMMON
    public static final String ROLE_ID = "J_ROLE_ID";
    public static final String OPERATION_ID = "J_OPERATION_ID";
    public static final String USER_ID = "J_USER_ID";

    //ROLE & OPERATION
    public static final String ROLE_JOIN_WITH_OPERATION = "J_ROLE_OPERATION";
    public static final String OPERATION_MAPPED_BY_ROLE = "operations";

    //USER & ROLE
    public static final String USER_JOIN_WITH_ROLE = "J_USER_ROLE";
    public static final String ROLE_MAPPED_BY_USER = "roles";

    //USER & PROJECT
    public static final String PROJECT_CREATOR_REFERENCE_USER = "creator";
    public static final String PROJECT_LEADER_REFERENCE_USER = "leader";

    //PROJECT * TASK
    public static final String TASK_REFERENCE_PROJECT = "project";

    public static final String TASK_REFERENCE_USER = "reporter";
}
