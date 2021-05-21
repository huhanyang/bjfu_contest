package com.bjfu.contest.pojo.request.process;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProcessDemoteGroupsRequest {
    /**
     * 进程id
     */
    @NotNull(message = "进程id不能为空")
    private Long processId;
    /**
     * 删除的队伍id
     */
    @NotNull(message = "删除的队伍id不能为空")
    private List<Long> groupIds;
}
