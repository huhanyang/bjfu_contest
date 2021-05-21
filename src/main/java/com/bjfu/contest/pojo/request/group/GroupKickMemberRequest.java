package com.bjfu.contest.pojo.request.group;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GroupKickMemberRequest {
    /**
     * 队伍id
     */
    @NotNull(message = "队伍id不能为空")
    private Long groupId;
    /**
     * 要踢出队员的账号
     */
    @NotEmpty(message = "提出队员的账号不能为空")
    private String userAccount;
}
