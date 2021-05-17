package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.UserGenderEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.pojo.dto.UserDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class UserVO {

    public UserVO() {}

    public UserVO(UserDTO userDTO) {
        this(userDTO, null);
    }

    public UserVO(UserDTO userDTO, String token) {
        BeanUtils.copyProperties(userDTO, this);
        this.token = token;
        this.inboxes = Optional.ofNullable(userDTO.getInboxes())
                .map(inboxes -> inboxes.stream().map(UserInboxVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.creatorContests = Optional.ofNullable(userDTO.getCreatorContests())
                .map(contestDTOS -> contestDTOS.stream().map(ContestVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.teacherContests = Optional.ofNullable(userDTO.getTeacherContests())
                .map(contestDTOS -> contestDTOS.stream().map(ContestVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.teacherGroups = Optional.ofNullable(userDTO.getTeacherGroups())
                .map(contestGroupDTOS -> contestGroupDTOS.stream().map(ContestGroupVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.contests = Optional.ofNullable(userDTO.getContests())
                .map(contestDTOS -> contestDTOS.stream().map(ContestVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.captainGroups = Optional.ofNullable(userDTO.getCaptainGroups())
                .map(contestGroupDTOS -> contestGroupDTOS.stream().map(ContestGroupVO::new).collect(Collectors.toList()))
                .orElse(null);
        this.groups = Optional.ofNullable(userDTO.getGroups())
                .map(contestGroupDTOS -> contestGroupDTOS.stream().map(ContestGroupVO::new).collect(Collectors.toList()))
                .orElse(null);
    }

    /**
     * 登录用token
     */
    private String token;
    /**
     * 用户id
     */
    private Long id;
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
    private List<UserInboxVO> inboxes;

    // 教师相关
    /**
     * 主持的竞赛
     */
    private List<ContestVO> creatorContests;
    /**
     * 当指导教师的竞赛
     */
    private List<ContestVO> teacherContests;
    /**
     * 指导的队伍
     */
    private List<ContestGroupVO> teacherGroups;

    // 学生相关
    /**
     * 报名的竞赛
     */
    private List<ContestVO> contests;
    /**
     * 领导的队伍
     */
    private List<ContestGroupVO> captainGroups;
    /**
     * 参加的队伍
     */
    private List<ContestGroupVO> groups;
}
