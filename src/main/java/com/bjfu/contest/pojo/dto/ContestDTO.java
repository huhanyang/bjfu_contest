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
     * ??????
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
     * ????????????
     */
    private String name;
    /**
     * ????????????
     */
    private String summary;
    /**
     * ????????????
     */
    private String description;
    /**
     * ???????????????
     */
    private UserDTO creator;
    /**
     * ????????????
     */
    private ContestStatusEnum status;
    /**
     * ??????????????????
     */
    private Integer groupMemberCount;
    /**
     * ????????????
     */
    private String extension;


    /**
     * ????????????
     */
    private List<ContestProcessDTO> processes;
    /**
     * ????????????????????????
     */
    private List<UserDTO> teachers;
    /**
     * ????????????
     */
    private List<ContestRegisterDTO> registers;
    /**
     * ????????????
     */
    private List<ContestGroupDTO> groups;
    /**
     * ????????????
     */
    private List<ContestAwardDTO> awards;

}
