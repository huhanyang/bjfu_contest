package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ContestDTO {

    public ContestDTO() {}
    public ContestDTO(Contest contest) {
        BeanUtils.copyProperties(contest, this, "creator");
        this.creator = new UserDTO(contest.getCreator());
    }


    /**
     * 主键
     */
    private Long id;
    /**
     * 竞赛名称
     */
    private String name;
    /**
     * 竞赛简介
     */
    private String summary;
    /**
     * 竞赛描述
     */
    private String description;
    /**
     * 竞赛创建人
     */
    private UserDTO creator;
    /**
     * 竞赛状态
     */
    private ContestStatusEnum status;
    /**
     * 队伍人数上限
     */
    private Integer groupMemberCount;
    /**
     * 扩展字段
     */
    private String extension;
    /**
     * 创建时间
     */
    private Date createdTime;

}
