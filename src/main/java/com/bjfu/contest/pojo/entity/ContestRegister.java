package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 竞赛报名登记实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class ContestRegister extends BaseEntity {
    /**
     * 报名的竞赛
     */
    @ManyToOne
    private Contest contest;
    /**
     * 报名的用户
     */
    @ManyToOne
    private User user;
    /**
     * 状态
     */
    @Enumerated
    @Column(nullable = false)
    private ContestRegisterStatusEnum status;
    /**
     * 参加的队伍
     */
    @OneToMany(mappedBy = "member")
    private List<ContestGroupMember> groups;
}
