package com.bjfu.contest.pojo.request.teacher;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeacherJoinGroupRequest {
    /**
     * 竞赛id
     */
    @NotNull(message = "竞赛id不能为空")
    private Long contestId;
    /**
     * 队伍id
     */
    @NotNull(message = "队伍id不能为空")
    private Long groupId;
}
