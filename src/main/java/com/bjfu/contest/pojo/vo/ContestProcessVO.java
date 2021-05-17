package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.pojo.dto.ContestProcessDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class ContestProcessVO {

    public ContestProcessVO() {}

    public ContestProcessVO(ContestProcessDTO processDTO) {
        BeanUtils.copyProperties(processDTO, this);
        this.contest = Optional.ofNullable(processDTO.getContest()).map(ContestVO::new).orElse(null);
        this.groups = Optional.ofNullable(processDTO.getGroups())
                .map(groups -> groups.stream().map(ContestProcessGroupVO::new).collect(Collectors.toList()))
                .orElse(null);
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
    private ContestVO contest;
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
    private List<ContestProcessGroupVO> groups;
}
