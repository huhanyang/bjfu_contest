package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.dto.ContestDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class ContestVO {

    public ContestVO() {}

    public ContestVO(ContestDTO contestDTO) {
        BeanUtils.copyProperties(contestDTO, this);
        this.creator = Optional.ofNullable(contestDTO.getCreator()).map(UserVO::new).orElse(null);
        this.processes = Optional.ofNullable(contestDTO.getProcesses())
                .map(processes -> processes.stream().map(ContestProcessVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.teachers = Optional.ofNullable(contestDTO.getTeachers())
                .map(teachers -> teachers.stream().map(UserVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.registers = Optional.ofNullable(contestDTO.getRegisters())
                .map(registers -> registers.stream().map(ContestRegisterVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.groups = Optional.ofNullable(contestDTO.getGroups())
                .map(groups -> groups.stream().map(ContestGroupVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.awards = Optional.ofNullable(contestDTO.getAwards())
                .map(awards -> awards.stream().map(ContestAwardVO::new).collect(Collectors.toList()))
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
     * 竞赛名称
     */
    private String name;
    /**
     * 竞赛简介
     */
    private String summary;
    /**
     * 竞赛描述
     */
    private String description;
    /**
     * 竞赛创建人
     */
    private UserVO creator;
    /**
     * 竞赛状态
     */
    private ContestStatusEnum status;
    /**
     * 队伍人数上限
     */
    private Integer groupMemberCount;
    /**
     * 扩展字段
     */
    private String extension;


    /**
     * 竞赛流程
     */
    private List<ContestProcessVO> processes;
    /**
     * 竞赛指导教师列表
     */
    private List<UserVO> teachers;
    /**
     * 竞赛报名
     */
    private List<ContestRegisterVO> registers;
    /**
     * 竞赛队伍
     */
    private List<ContestGroupVO> groups;
    /**
     * 奖项列表
     */
    private List<ContestAwardVO> awards;
}
