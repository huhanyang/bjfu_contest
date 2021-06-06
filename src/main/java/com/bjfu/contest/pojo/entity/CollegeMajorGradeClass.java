package com.bjfu.contest.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 班级实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class CollegeMajorGradeClass extends BaseEntity {
    /**
     * 学院
     */
    @Column(length=32, nullable=false)
    private String college;
    /**
     * 专业
     */
    @Column(length=32)
    private String major;
    /**
     * 年级
     */
    @Column(length=32)
    private String grade;
    /**
     * 班级
     */
    @Column(length=32)
    private String company;
}
