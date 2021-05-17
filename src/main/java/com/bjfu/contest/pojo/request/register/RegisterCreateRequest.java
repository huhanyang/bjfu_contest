package com.bjfu.contest.pojo.request.register;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterCreateRequest {
    /**
     * 竞赛id
     */
    @NotNull(message = "竞赛id不能为空")
    private Long contestId;
}
