package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.pojo.entity.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContestGroupDTO {

    public  ContestGroupDTO() {}

    public  ContestGroupDTO(ContestGroup contestGroup,
                            boolean needContest, boolean needCaptain, boolean needTeacher,
                            boolean needMembers, boolean needProcesses, boolean needAwards) {
        if(contestGroup != null) {
            BeanUtils.copyProperties(contestGroup, this,
                    "contest", "captain", "teacher",
                    "members", "processes", "awards");
            if(needContest) {
                this.contest = new ContestDTO(contestGroup.getContest(), true, false, false, false, false, false);
            }
            if(needCaptain) {
                this.captain = new UserDTO(contestGroup.getCaptain());
            }
            if(needTeacher) {
                this.teacher = new UserDTO(contestGroup.getTeacher());
            }
            if(needMembers) {
                this.members = contestGroup.getMembers()
                        .stream()
                        .map(contestGroupMember -> new UserDTO(contestGroupMember.getMember().getUser()))
                        .collect(Collectors.toList());
            }
            if(needProcesses) {
                this.processes = contestGroup.getProcesses()
                        .stream()
                        .map(contestProcessGroup -> new ContestProcessGroupDTO(contestProcessGroup, true, false))
                        .collect(Collectors.toList());
            }
            if(needAwards) {
                this.awards = contestGroup.getAwards()
                        .stream()
                        .map(ContestAward::getName)
                        .collect(Collectors.toList());
            }
        }
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
    private ContestDTO contest;
    /**
     * 队伍名
     */
    private String name;
    /**
     * 队长
     */
    private UserDTO captain;
    /**
     * 指导教师
     */
    private UserDTO teacher;
    /**
     * 队伍介绍
     */
    private String introduction;

    /**
     * 队伍成员
     */
    private List<UserDTO> members;
    /**
     * 所在的流程
     */
    private List<ContestProcessGroupDTO> processes;
    /**
     * 获得的奖项
     */
    private List<String> awards;
}
