package com.bjfu.contest.pojo.request.contest;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ContestCreateRequest {
    /**
     * 竞赛名称
     */
    @NotBlank(message = "竞赛名称不能为空!")
    @Length(max = 32, message = "竞赛名称最大32位!")
    private String name;
    /**
     * 竞赛简介
     */
    @NotBlank(message = "竞赛简介不能为空!")
    @Length(max = 128, message = "竞赛简介最大128位!")
    private String summary;
    /**
     * 竞赛描述
     */
    @NotBlank(message = "竞赛描述不能为空!")
    @Length(max = 512, message = "竞赛描述最大512位!")
    private String description;
    /**
     * 队伍人数上限
     */
    @NotNull(message = "队伍人数上限不能为空!")
    private Integer groupMemberCount;
}
