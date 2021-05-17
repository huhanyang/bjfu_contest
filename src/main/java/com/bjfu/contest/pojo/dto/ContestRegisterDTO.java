package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
public class ContestRegisterDTO {

    public ContestRegisterDTO() {}

    public ContestRegisterDTO(ContestRegister contestRegister, boolean needContest, boolean needUser) {
        if(contestRegister != null) {
            BeanUtils.copyProperties(contestRegister, this, "contest", "user");
            if(needContest) {
                this.contest = new ContestDTO(contestRegister.getContest(),
                        true, false,
                        false, false,
                        false, false);
            }
            if(needUser) {
                this.user = new UserDTO(contestRegister.getUser());
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
     * 报名的竞赛
     */
    private ContestDTO contest;
    /**
     * 报名的用户
     */
    private UserDTO user;
    /**
     * 状态
     */
    private ContestRegisterStatusEnum status;
}
