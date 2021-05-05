package com.bjfu.contest.pojo.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserActivateRequest {
    @NotBlank(message = "token不能为空!")
    private String token;
}
