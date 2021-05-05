package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.UserGenderEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.pojo.dto.UserDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserVO {
    public UserVO() {

    }

    public UserVO(UserDTO userDTO, String token) {
        BeanUtils.copyProperties(userDTO, this);
        this.token = token;
    }

    private Long id;
    private String token;
    private String account;
    private String email;
    private UserTypeEnum type;
    private UserStatusEnum status;
    private String name;
    private UserGenderEnum gender;
    private String grade;
    private String college;
    private String major;
    private String introduction;
}
