package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.pojo.dto.ContestAwardDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Optional;

@Data
public class ContestAwardVO {

    public ContestAwardVO() {}

    public ContestAwardVO(ContestAwardDTO contestAwardDTO) {
        BeanUtils.copyProperties(contestAwardDTO, this);
        this.contest = Optional.ofNullable(contestAwardDTO.getContest()).map(ContestVO::new).orElse(null);
        this.group = Optional.ofNullable(contestAwardDTO.getGroup()).map(ContestGroupVO::new).orElse(null);
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
     * 对应的竞赛
     */
    private ContestVO contest;
    /**
     * 获奖队伍
     */
    private ContestGroupVO group;
    /**
     * 奖项名称
     */
    private String name;
}
