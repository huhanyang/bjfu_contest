package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ContestVO {

    public ContestVO() {}
    public ContestVO(ContestDTO contestDTO) {
        BeanUtils.copyProperties(contestDTO, this, "creator");
        this.setCreator(new UserVO(contestDTO.getCreator(), null));
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
    private UserVO creator;
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
}
