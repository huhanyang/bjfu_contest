package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.NotifyTypeEnum;
import com.bjfu.contest.pojo.dto.NotifyDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Optional;

@Data
public class NotifyVO {

    public NotifyVO() {}

    public NotifyVO(NotifyDTO notifyDTO) {
        BeanUtils.copyProperties(notifyDTO, this);
        this.author = Optional.ofNullable(notifyDTO.getAuthor()).map(UserVO::new).orElse(null);
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
    private UserVO author;
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
