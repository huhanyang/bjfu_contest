package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.pojo.entity.ContestAward;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ContestAwardDTO {

    public ContestAwardDTO() {}

    public ContestAwardDTO(ContestAward contestAward, boolean needContest, boolean needGroup) {
        if(contestAward != null) {
            BeanUtils.copyProperties(contestAward, this, "contest", "group");
            if(needContest) {
                this.contest = new ContestDTO(contestAward.getContest(),
                        true, false,
                        false, false,
                        false, false);
            }
            if(needGroup) {
                this.group = new ContestGroupDTO(contestAward.getGroup(),
                        false, true, true,
                        false, false, false);
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
     * 对应的竞赛
     */
    private ContestDTO contest;
    /**
     * 获奖队伍
     */
    private ContestGroupDTO group;
    /**
     * 奖项名称
     */
    private String name;
}
