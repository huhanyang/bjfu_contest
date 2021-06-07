package com.bjfu.contest.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 竞赛队伍成员表
 * @author warthog
 */
@Getter
@Setter
@Entity
public class ContestGroupMember extends BaseEntity {

    /**
     * 对应的竞赛(冗余字段)
     */
    @ManyToOne
    private Contest contest;
    /**
     * 加入的队伍
     */
    @ManyToOne
    private ContestGroup group;
    /**
     * 加入的成员
     */
    @ManyToOne
    private ContestRegister member;
}
