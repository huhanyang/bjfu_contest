package com.bjfu.contest.pojo.request.group;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GroupJoinRequest {
    /**
     * 队伍id
     */
    @NotNull(message = "队伍id不能为空")
    private Long groupId;
}
