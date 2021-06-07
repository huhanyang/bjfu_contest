package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.enums.UserGenderEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {

    public UserDTO() {}

    public UserDTO(User user) {
        this(user, false,
                false, false, false,
                false, false, false);
    }

    public UserDTO(User user, boolean needInboxes,
                   boolean needCreatorContests, boolean needTeacherContests, boolean needTeacherGroups,
                   boolean needContests, boolean needCaptainGroups, boolean needGroups) {
        if(user != null) {
            BeanUtils.copyProperties(user, this, "inboxes",
                    "creatorContests", "teacherContests", "teacherGroups",
                    "contests", "captainGroups", "groups");
          if(needInboxes) {
              this.inboxes = user.getInboxes()
                      .stream()
                      .map(inbox -> new UserInboxDTO(inbox, false, true))
                      .collect(Collectors.toList());
          }
          if(needCreatorContests) {
              this.creatorContests = user.getCreatorContests()
                      .stream()
                      .map(contest -> new ContestDTO(contest,
                              false, false,
                              false, false,
                              false, false))
                      .collect(Collectors.toList());
          }
          if(needTeacherContests) {
              this.teacherContests = user.getTeacherContests()
                      .stream()
                      .map(contestTeacher -> new ContestDTO(contestTeacher.getContest(),
                              true, false,
                              false, false,
                              false, false))
                      .collect(Collectors.toList());
          }
          if(needTeacherGroups) {
              this.teacherGroups = user.getTeacherGroups()
                      .stream()
                      .map(group -> new ContestGroupDTO(group,
                              true, true, false,
                              false, false, false))
                      .collect(Collectors.toList());
          }
          if(needContests) {
              this.contests = user.getContests()
                      .stream()
                      .filter(contestRegister -> contestRegister.getStatus().equals(ContestRegisterStatusEnum.SIGN_UP))
                      .map(contestRegister -> new ContestDTO(contestRegister.getContest(),
                              true, false,
                              false, false,
                              false, false))
                      .collect(Collectors.toList());
          }
          if(needCaptainGroups) {
              this.captainGroups = user.getCaptainGroups()
                      .stream()
                      .map(group -> new ContestGroupDTO(group,
                              true, false, true,
                              false, false, false))
                      .collect(Collectors.toList());
          }
          if(needGroups) {
              this.groups = user.getContests()
                      .stream()
                      .map(ContestRegister::getGroups)
                      .flatMap(Collection::stream)
                      .map(contestGroupMember -> new ContestGroupDTO(contestGroupMember.getGroup(),
                              true, true, true,
                              false, false, false))
                      .collect(Collectors.toList());
          }
        }
    }

    /**
     * 用户id
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
     * 账号
     */
    private String account;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户类型
     */
    private UserTypeEnum type;
    /**
     * 账号状态
     */
    private UserStatusEnum status;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 性别
     */
    private UserGenderEnum gender;
    /**
     * 入学年级
     */
    private String grade;
    /**
     * 学院
     */
    private String college;
    /**
     * 专业
     */
    private String major;
    /**
     * 介绍
     */
    private String introduction;

    /**
     * 收件箱
     */
    private List<UserInboxDTO> inboxes;

    // 教师相关
    /**
     * 主持的竞赛
     */
    private List<ContestDTO> creatorContests;
    /**
     * 当指导教师的竞赛
     */
    private List<ContestDTO> teacherContests;
    /**
     * 指导的队伍
     */
    private List<ContestGroupDTO> teacherGroups;

    // 学生相关
    /**
     * 报名的竞赛
     */
    private List<ContestDTO> contests;
    /**
     * 领导的队伍
     */
    private List<ContestGroupDTO> captainGroups;
    /**
     * 参加的队伍
     */
    private List<ContestGroupDTO> groups;
}
