package com.bjfu.contest.pojo.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserResetPasswordRequest {
    /**
     * 验证用的token
     */
    @NotBlank(message = "token不能为空!")
    private String token;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    @Length(min = 8, max = 32, message = "密码长度在8-32位!")
    private String password;
}
