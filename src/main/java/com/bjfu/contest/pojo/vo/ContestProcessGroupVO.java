package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.ContestProcessGroupStatusEnum;
import com.bjfu.contest.pojo.dto.ContestProcessGroupDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Optional;

@Data
public class ContestProcessGroupVO {

    public ContestProcessGroupVO() {}

    public ContestProcessGroupVO(ContestProcessGroupDTO contestProcessGroupDTO) {
        BeanUtils.copyProperties(contestProcessGroupDTO, this);
        this.process = Optional.ofNullable(contestProcessGroupDTO.getProcess()).map(ContestProcessVO::new).orElse(null);
        this.group = Optional.ofNullable(contestProcessGroupDTO.getGroup()).map(ContestGroupVO::new).orElse(null);
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
     * 流程
     */
    private ContestProcessVO process;
    /**
     * 队伍
     */
    private ContestGroupVO group;
    /**
     * 状态
     */
    private ContestProcessGroupStatusEnum status;
    /**
     * 提交的内容(json array)
     */
    private String submitList;
}
