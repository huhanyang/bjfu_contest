package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.pojo.entity.ContestAward;
import com.bjfu.contest.pojo.entity.ContestGroup;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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
     * ??????id
     */
    private Long id;
    /**
     * ????????????
     */
    private Date createdTime;
    /**
     * ????????????
     */
    private Date lastModifiedTime;
    /**
     * ???????????????
     */
    private ContestDTO contest;
    /**
     * ?????????
     */
    private String name;
    /**
     * ??????
     */
    private UserDTO captain;
    /**
     * ????????????
     */
    private UserDTO teacher;
    /**
     * ????????????
     */
    private String introduction;

    /**
     * ????????????
     */
    private List<UserDTO> members;
    /**
     * ???????????????
     */
    private List<ContestProcessGroupDTO> processes;
    /**
     * ???????????????
     */
    private List<String> awards;
}
