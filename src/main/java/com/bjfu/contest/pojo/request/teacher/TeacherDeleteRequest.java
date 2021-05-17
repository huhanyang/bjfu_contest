package com.bjfu.contest.pojo.request.teacher;

import lombok.Data;

@Data
public class TeacherDeleteRequest {
    private Long contestId;
    private String teacherAccount;
}
