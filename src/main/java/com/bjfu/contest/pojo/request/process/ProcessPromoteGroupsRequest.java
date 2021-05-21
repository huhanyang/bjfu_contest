package com.bjfu.contest.pojo.request.process;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProcessPromoteGroupsRequest {
    /**
     * 晋级到的进程id
     */
    @NotNull(message = "目标进程id不能为空")
    private Long processId;
    /**
     * 从上一流程晋级到的此进程的队伍id列表
     */
    @NotNull(message = "选择的队伍id不能为空")
    private List<Long> groupIds;
}
