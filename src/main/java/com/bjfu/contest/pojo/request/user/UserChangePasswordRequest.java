package com.bjfu.contest.pojo.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserChangePasswordRequest {
    /**
     * 密码
     */
    @NotBlank(message = "原密码不能为空!")
    @Length(min = 8, max = 32, message = "原密码长度在8-32位!")
    private String oldPassword;
    /**
     * 密码
     */
    @NotBlank(message = "新密码不能为空!")
    @Length(min = 8, max = 32, message = "新密码长度在8-32位!")
    private String newPassword;
}
