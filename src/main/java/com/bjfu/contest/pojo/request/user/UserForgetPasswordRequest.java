package com.bjfu.contest.pojo.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserForgetPasswordRequest {
    /**
     * 邮箱
     */
    @Email
    @NotBlank(message = "邮箱不能为空!")
    @Length(max = 32, message = "邮箱长度最大32位!")
    private String email;
}
