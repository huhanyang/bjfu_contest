package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.ContestProcessGroupStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * 竞赛流程队伍实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class ContestProcessGroup extends BaseEntity {
    /**
     * 流程
     */
    @ManyToOne
    private ContestProcess process;
    /**
     * 队伍
     */
    @ManyToOne
    private ContestGroup group;
    /**
     * 状态
     */
    @Enumerated
    @Column(nullable = false)
    private ContestProcessGroupStatusEnum status;
    /**
     * 提交的内容(json array)
     */
    @Column(length=512)
    private String submitList;
}
