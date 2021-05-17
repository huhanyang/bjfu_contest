package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.pojo.dto.ContestGroupDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class ContestGroupVO {

    public ContestGroupVO() {}

    public ContestGroupVO(ContestGroupDTO contestGroupDTO) {
        BeanUtils.copyProperties(contestGroupDTO, this);
        this.contest = Optional.ofNullable(contestGroupDTO.getContest()).map(ContestVO::new).orElse(null);
        this.captain = Optional.ofNullable(contestGroupDTO.getCaptain()).map(UserVO::new).orElse(null);
        this.teacher = Optional.ofNullable(contestGroupDTO.getTeacher()).map(UserVO::new).orElse(null);
        this.members = Optional.ofNullable(contestGroupDTO.getMembers())
                .map(members -> members.stream().map(UserVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.processes = Optional.ofNullable(contestGroupDTO.getProcesses())
                .map(processes -> processes.stream().map(ContestProcessGroupVO::new).collect(Collectors.toList()))
                .orElse(null);
    }

    /**
     * 队伍id
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
     * 对应的竞赛
     */
    private ContestVO contest;
    /**
     * 队伍名
     */
    private String name;
    /**
     * 队长
     */
    private UserVO captain;
    /**
     * 指导教师
     */
    private UserVO teacher;
    /**
     * 队伍介绍
     */
    private String introduction;

    /**
     * 队伍成员
     */
    private List<UserVO> members;
    /**
     * 所在的流程
     */
    private List<ContestProcessGroupVO> processes;
    /**
     * 获得的奖项
     */
    private List<String> awards;
}
