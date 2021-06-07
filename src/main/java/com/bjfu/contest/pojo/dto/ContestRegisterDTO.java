package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.entity.ContestRegister;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ContestRegisterDTO {

    public ContestRegisterDTO() {}

    public ContestRegisterDTO(ContestRegister contestRegister, boolean needContest, boolean needUser, boolean needGroups) {
        if(contestRegister != null) {
            BeanUtils.copyProperties(contestRegister, this, "contest", "user");
            if(needContest) {
                this.contest = new ContestDTO(contestRegister.getContest(),
                        true, false,
                        false, false,
                        false, false);
            }
            if(needUser) {
                this.user = new UserDTO(contestRegister.getUser());
            }
            if (needGroups) {
                this.groups = contestRegister.getGroups().stream()
                        .map(contestGroupMember -> new ContestGroupDTO(contestGroupMember.getGroup(),
                                false, true, true,
                                false, false, false))
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
     * 报名的竞赛
     */
    private ContestDTO contest;
    /**
     * 报名的用户
     */
    private UserDTO user;
    /**
     * 状态
     */
    private ContestRegisterStatusEnum status;
    /**
     * 参加的队伍
     */
    private List<ContestGroupDTO> groups;
}
