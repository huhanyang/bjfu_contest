package com.bjfu.contest.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 竞赛奖品表
 * @author warthog
 */
@Getter
@Setter
@Entity
public class ContestAward extends BaseEntity {
    /**
     * 对应的竞赛(冗余字段)
     */
    @ManyToOne
    private Contest contest;
    /**
     * 奖项名称
     */
    @Column(length = 32, nullable=false)
    private String name;
    /**
     * 获奖队伍
     */
    @ManyToOne
    private ContestGroup group;
}
