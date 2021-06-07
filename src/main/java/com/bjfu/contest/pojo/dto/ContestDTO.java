package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContestDTO {

    public ContestDTO() {}

    public ContestDTO(Contest contest, boolean needCreator, boolean needProcesses, boolean needTeachers,
                      boolean needRegisters, boolean needGroups, boolean needAwards) {
        if(contest != null) {
            BeanUtils.copyProperties(contest, this,
                    "creator", "processes", "teachers",
                    "registers", "groups", "awards");
            if(needCreator) {
                this.creator = new UserDTO(contest.getCreator());
            }
            if(needProcesses) {
                this.processes = contest.getProcesses()
                        .stream()
                        .map(process -> new ContestProcessDTO(process, false, false))
                        .collect(Collectors.toList());
            }
            if(needTeachers) {
                this.teachers = contest.getTeachers()
                        .stream()
                        .map(contestTeacher -> new UserDTO(contestTeacher.getTeacher()))
                        .collect(Collectors.toList());
            }
            if(needRegisters) {
                this.registers = contest.getRegisters()
                        .stream()
                        .map(contestRegister -> new ContestRegisterDTO(contestRegister, false, true, false))
                        .collect(Collectors.toList());
            }
            if(needGroups) {
                this.groups = contest.getGroups()
                        .stream()
                        .map(group -> new ContestGroupDTO(group,
                                false, true, true,
                                false, false, false))
                        .collect(Collectors.toList());
            }
            if(needAwards) {
                this.awards = contest.getAwards()
                        .stream()
                        .map(contestAward -> new ContestAwardDTO(contestAward, false, true))
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
    private UserDTO creator;
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
    private List<ContestProcessDTO> processes;
    /**
     * 竞赛指导教师列表
     */
    private List<UserDTO> teachers;
    /**
     * 竞赛报名
     */
    private List<ContestRegisterDTO> registers;
    /**
     * 竞赛队伍
     */
    private List<ContestGroupDTO> groups;
    /**
     * 奖项列表
     */
    private List<ContestAwardDTO> awards;

}
