package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.NotifyTypeEnum;
import com.bjfu.contest.pojo.entity.Notify;
import com.bjfu.contest.pojo.entity.Resource;
import com.bjfu.contest.pojo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
public class NotifyDTO {

    public NotifyDTO() {}

    public NotifyDTO(Notify notify, boolean needAuthor) {
        if(notify != null) {
            BeanUtils.copyProperties(notify, this, "author");
            if(needAuthor) {
                this.author = new UserDTO(notify.getAuthor());
            }
        }
    }

    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date lastModifiedTime;

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 作者
     */
    private UserDTO author;
    /**
     * 类型
     */
    private NotifyTypeEnum type;
    /**
     * 所属目标
     */
    private Long targetId;
    /**
     * 是否发送邮件
     */
    private Boolean isSendEmail;
}
