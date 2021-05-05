package com.bjfu.contest.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 竞赛指导教师多对多关系中间表
 * @author warthog
 */
@Getter
@Setter
@Entity
public class ContestTeacher extends BaseEntity {
    /**
     * 竞赛
     */
    @ManyToOne
    private Contest contest;
    /**
     * 教师
     */
    @ManyToOne
    private User teacher;
}
