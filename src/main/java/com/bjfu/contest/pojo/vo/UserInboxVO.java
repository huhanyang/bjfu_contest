package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.pojo.dto.UserInboxDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Optional;

@Data
public class UserInboxVO {

    public UserInboxVO() {}

    public UserInboxVO(UserInboxDTO userInboxDTO) {
        BeanUtils.copyProperties(userInboxDTO, this);
        this.user = Optional.ofNullable(userInboxDTO.getUser()).map(UserVO::new).orElse(null);
        this.notify = Optional.ofNullable(userInboxDTO.getNotify()).map(NotifyVO::new).orElse(null);
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
    private UserVO user;
    /**
     * 通知
     */
    private NotifyVO notify;
    /**
     * 是否已读
     */
    private Boolean isRead;
    /**
     * 邮件是否发送成功
     */
    private Boolean isSendEmailSuccess;
}
