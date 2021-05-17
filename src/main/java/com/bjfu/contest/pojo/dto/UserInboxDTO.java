package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.pojo.entity.Notify;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.entity.UserInbox;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
public class UserInboxDTO {

    public UserInboxDTO() {}

    public UserInboxDTO(UserInbox userInbox, boolean needUser, boolean needNotify) {
        if(userInbox != null) {
            BeanUtils.copyProperties(userInbox, this, "user", "notify");
            if(needUser) {
                this.user = new UserDTO(userInbox.getUser());
            }
            if(needNotify) {
                this.notify = new NotifyDTO(userInbox.getNotify(), true);
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
     * 接收人
     */
    private UserDTO user;
    /**
     * 通知
     */
    private NotifyDTO notify;
    /**
     * 是否已读
     */
    private Boolean isRead;
    /**
     * 邮件是否发送成功
     */
    private Boolean isSendEmailSuccess;
}
