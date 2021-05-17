package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.dto.ContestRegisterDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Optional;

@Data
public class ContestRegisterVO {

    public ContestRegisterVO() {}

    public ContestRegisterVO(ContestRegisterDTO contestRegisterDTO) {
        BeanUtils.copyProperties(contestRegisterDTO, this);
        this.contest = Optional.ofNullable(contestRegisterDTO.getContest()).map(ContestVO::new).orElse(null);
        this.user = Optional.ofNullable(contestRegisterDTO.getUser()).map(UserVO::new).orElse(null);
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
     * 报名的竞赛
     */
    private ContestVO contest;
    /**
     * 报名的用户
     */
    private UserVO user;
    /**
     * 状态
     */
    private ContestRegisterStatusEnum status;

}
