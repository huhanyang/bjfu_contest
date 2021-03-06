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
     * ??????
     */
    private String account;
    /**
     * ??????
     */
    private String email;
    /**
     * ????????????
     */
    private UserTypeEnum type;
    /**
     * ????????????
     */
    private UserStatusEnum status;
    /**
     * ????????????
     */
    private String name;
    /**
     * ??????
     */
    private UserGenderEnum gender;
    /**
     * ????????????
     */
    private String grade;
    /**
     * ??????
     */
    private String college;
    /**
     * ??????
     */
    private String major;
    /**
     * ??????
     */
    private String introduction;

    /**
     * ?????????
     */
    private List<UserInboxDTO> inboxes;

    // ????????????
    /**
     * ???????????????
     */
    private List<ContestDTO> creatorContests;
    /**
     * ????????????????????????
     */
    private List<ContestDTO> teacherContests;
    /**
     * ???????????????
     */
    private List<ContestGroupDTO> teacherGroups;

    // ????????????
    /**
     * ???????????????
     */
    private List<ContestDTO> contests;
    /**
     * ???????????????
     */
    private List<ContestGroupDTO> captainGroups;
    /**
     * ???????????????
     */
    private List<ContestGroupDTO> groups;
}
