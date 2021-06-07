package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ContestProcessGroupStatusEnum;
import com.bjfu.contest.pojo.entity.ContestProcessGroup;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ContestProcessGroupDTO {

    public ContestProcessGroupDTO() {}

    public ContestProcessGroupDTO(ContestProcessGroup contestProcessGroup, boolean needProcess, boolean needGroup) {
        if(contestProcessGroup != null) {
            BeanUtils.copyProperties(contestProcessGroup, this, "process", "group");
            if(needProcess) {
                this.process = new ContestProcessDTO(contestProcessGroup.getProcess(), false, false);
            }
            if(needGroup) {
                this.group = new ContestGroupDTO(contestProcessGroup.getGroup(),
                        false, true, true,
                        false, false, false);
            }
        }
    }


    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date lastModifiedTime;

    /**
     * 流程
     */
    private ContestProcessDTO process;
    /**
     * 队伍
     */
    private ContestGroupDTO group;
    /**
     * 状态
     */
    private ContestProcessGroupStatusEnum status;
    /**
     * 提交的内容(json array)
     */
    private String submitList;
}
