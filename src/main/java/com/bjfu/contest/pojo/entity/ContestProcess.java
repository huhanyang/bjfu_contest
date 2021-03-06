package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.ContestProcessStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 竞赛流程实体
 * @author warthog
 */
@Getter
@Setter
@Entity
@Where(clause = "status != 3")
public class ContestProcess extends BaseEntity {
    /**
     * 对应竞赛
     */
    @ManyToOne
    private Contest contest;
    /**
     * 流程名
     */
    @Column(length=32, nullable=false)
    private String name;
    /**
     * 流程序号
     */
    @Column(nullable=false)
    private Integer sort;
    /**
     * 流程状态
     */
    @Enumerated
    @Column(nullable = false)
    private ContestProcessStatusEnum status;
    /**
     * 流程描述
     */
    @Column(length=512, nullable=false)
    private String description;
    /**
     * 需要提交的内容(json array)
     */
    @Column(length=512)
    private String submitList;
    /**
     * 停止提交的时间
     */
    @Column(nullable=false)
    private Date endSubmitTime;
    /**
     * 流程开始的时间
     */
    @Column(nullable=true)
    private Date startTime;
    /**
     * 流程结束的时间
     */
    @Column(nullable=true)
    private Date finishTime;

    /**
     * 流程中的队伍
     */
    @OneToMany(mappedBy = "process")
    private List<ContestProcessGroup> groups;
}
