package com.bjfu.contest.pojo.request.teacher;

import lombok.Data;

import java.util.List;

@Data
public class TeacherCreateRequest {
    private Long contestId;
    private List<String> teacherAccounts;
}
