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
     * ?????????token
     */
    private String token;
    /**
     * ??????id
     */
    private Long id;
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
    private List<UserInboxVO> inboxes;

    // ????????????
    /**
     * ???????????????
     */
    private List<ContestVO> creatorContests;
    /**
     * ????????????????????????
     */
    private List<ContestVO> teacherContests;
    /**
     * ???????????????
     */
    private List<ContestGroupVO> teacherGroups;

    // ????????????
    /**
     * ???????????????
     */
    private List<ContestVO> contests;
    /**
     * ???????????????
     */
    private List<ContestGroupVO> captainGroups;
    /**
     * ???????????????
     */
    private List<ContestGroupVO> groups;
}
