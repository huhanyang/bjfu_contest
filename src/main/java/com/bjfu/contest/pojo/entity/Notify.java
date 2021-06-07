package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.NotifyTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * 通知实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class Notify extends BaseEntity {
    /**
     * 标题
     */
    @Column(length=32, nullable=false)
    private String title;
    /**
     * 内容
     */
    @Column(length=128, nullable=false)
    private String content;
    /**
     * 作者
     */
    @ManyToOne
    private User author;
    /**
     * 类型
     */
    @Enumerated
    @Column(nullable=false)
    private NotifyTypeEnum type;
    /**
     * 所属目标
     */
    private Long targetId;
    /**
     * 是否发送邮件
     */
    @Column(nullable=false)
    private Boolean isSendEmail;
}
