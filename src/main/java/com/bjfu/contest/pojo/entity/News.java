package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.NewsTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 新闻实体
 */
@Getter
@Setter
@Entity
public class News extends BaseEntity {
    /**
     * 标题
     */
    @Column(length=32, nullable=false)
    private String title;
    /**
     * 摘要
     */
    @Column(length=128, nullable=false)
    private String summary;
    /**
     * 类型
     */
    @Enumerated
    @Column(nullable=false)
    private NewsTypeEnum type;
    /**
     * 作者
     */
    @ManyToOne
    private User author;
    /**
     * 内容
     */
    @Column(length=128, nullable=false)
    private String content;
    /**
     * 所属目标
     */
    private Long targetId;
    /**
     * 携带的资源
     */
    @OneToOne
    private Resource resource;
}
