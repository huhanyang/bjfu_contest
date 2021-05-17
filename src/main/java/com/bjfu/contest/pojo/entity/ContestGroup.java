package com.bjfu.contest.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 竞赛队伍实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class ContestGroup extends BaseEntity {
    /**
     * 对应的竞赛
     */
    @ManyToOne
    private Contest contest;
    /**
     * 队伍名
     */
    @Column(length = 32, nullable = false)
    private String name;
    /**
     * 队长
     */
    @ManyToOne
    private User captain;
    /**
     * 指导教师
     */
    @ManyToOne
    private User teacher;
    /**
     * 队伍介绍
     */
    @Column(length=256)
    private String introduction;

    /**
     * 队伍成员
     */
    @OneToMany(mappedBy = "group")
    private List<ContestGroupMember> members;
    /**
     * 所在的流程
     */
    @OneToMany(mappedBy = "group")
    private List<ContestProcessGroup> processes;
    /**
     * 获得的奖项
     */
    @OneToMany(mappedBy = "group")
    private List<ContestAward> awards;
}
