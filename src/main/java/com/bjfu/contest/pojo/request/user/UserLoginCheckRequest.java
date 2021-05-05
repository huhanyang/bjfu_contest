package com.bjfu.contest.pojo.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginCheckRequest {
    /**
     * 学工号/邮箱
     */
    @NotBlank(message = "学工号/邮箱不能为空!")
    @Length(min = 8, max = 32, message = "学工号/邮箱长度在8-32位!")
    private String account;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    @Length(min = 8, max = 32, message = "密码长度在8-32位!")
    private String password;
}
