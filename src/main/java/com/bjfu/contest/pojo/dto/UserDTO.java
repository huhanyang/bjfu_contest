package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.UserGenderEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.pojo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserDTO {
    public UserDTO() {}
    public UserDTO(User user) {
        BeanUtils.copyProperties(user, this);
    }
    private Long id;
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
