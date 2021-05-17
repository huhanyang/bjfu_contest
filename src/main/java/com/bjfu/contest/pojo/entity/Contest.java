package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.ContestStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 竞赛实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class Contest extends BaseEntity {
    /**
     * 竞赛名称
     */
    @Column(length=32, nullable=false)
    private String name;
    /**
     * 竞赛简介
     */
    @Column(length=128, nullable=false)
    private String summary;
    /**
     * 竞赛描述
     */
    @Column(length=512, nullable=false)
    private String description;
    /**
     * 竞赛创建人
     */
    @ManyToOne
    private User creator;
    /**
     * 竞赛状态
     */
    @Enumerated
    @Column(nullable=false)
    private ContestStatusEnum status;
    /**
     * 队伍人数上限
     */
    @Column(nullable=false)
    private Integer groupMemberCount;
    /**
     * 扩展字段
     */
    @Column(length=512)
    private String extension;


    /**
     * 竞赛流程
     */
    @OneToMany(mappedBy = "contest")
    @OrderBy("sort asc")
    private List<ContestProcess> processes;
    /**
     * 竞赛指导教师列表
     */
    @OneToMany(mappedBy = "contest")
    private List<ContestTeacher> teachers;
    /**
     * 竞赛报名
     */
    @OneToMany(mappedBy = "contest")
    private List<ContestRegister> registers;
    /**
     * 竞赛队伍
     */
    @OneToMany(mappedBy = "contest")
    private List<ContestGroup> groups;
    /**
     * 奖项列表
     */
    @OneToMany(mappedBy = "contest")
    private List<ContestAward> awards;

}
