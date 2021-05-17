package com.bjfu.contest.pojo.request.group;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GroupEditRequest {
    /**
     * 队伍id
     */
    @NotNull(message = "队伍id不能为空")
    private Long groupId;
    /**
     * 队伍名
     */
    @NotEmpty(message = "队伍名不能为空")
    @Length(max = 32,min = 1, message = "队伍名长度1-32!")
    private String name;
    /**
     * 队伍介绍
     */
    @Length(max = 256, message = "队伍描述最长256!")
    private String introduction;
}
