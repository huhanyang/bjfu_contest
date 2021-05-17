package com.bjfu.contest.pojo.request.user;

import com.bjfu.contest.enums.UserTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class UserSearchRequest {
    private String name;
    private List<UserTypeEnum> types;
}
