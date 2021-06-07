package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.pojo.entity.ContestProcess;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContestProcessDTO {

    public ContestProcessDTO() {}

    public ContestProcessDTO(ContestProcess contestProcess, boolean needContest, boolean needGroups) {
        if(contestProcess != null) {
            BeanUtils.copyProperties(contestProcess, this, "contest", "group");
            if(needContest) {
                this.contest = new ContestDTO(contestProcess.getContest(),
                        true, false,
                        false, false,
                        false, false);
            }
            if(needGroups) {
                this.groups = contestProcess.getGroups()
                        .stream()
                        .map(contestProcessGroup -> new ContestProcessGroupDTO(contestProcessGroup, false, true))
                        .collect(Collectors.toList());
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
     * 对应竞赛
     */
    private ContestDTO contest;
    /**
     * 流程名
     */
    private String name;
    /**
     * 流程序号
     */
    private Integer sort;
    /**
     * 流程状态
     */
    private ContestProcessStatusEnum status;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 需要提交的内容(json array)
     */
    private String submitList;
    /**
     * 停止提交的时间
     */
    private Date endSubmitTime;
    /**
     * 流程开始的时间
     */
    private Date startTime;
    /**
     * 流程结束的时间
     */
    private Date finishTime;

    /**
     * 流程中的队伍
     */
    private List<ContestProcessGroupDTO> groups;
}
