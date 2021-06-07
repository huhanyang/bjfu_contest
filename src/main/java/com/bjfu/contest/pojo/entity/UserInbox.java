package com.bjfu.contest.pojo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 用户收件箱实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class UserInbox extends BaseEntity {
    /**
     * 接收人
     */
    @ManyToOne
    private User user;
    /**
     * 通知
     */
    @ManyToOne
    private Notify notify;
    /**
     * 是否已读
     */
    @Column(nullable=false)
    private Boolean isRead;
    /**
     * 邮件是否发送成功
     */
    @Column(nullable=true)
    private Boolean isSendEmailSuccess;
}
