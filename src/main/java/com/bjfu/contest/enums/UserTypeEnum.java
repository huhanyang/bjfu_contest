package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 用户账号类型枚举
 * @author warthog
 */
@Getter
public enum UserTypeEnum {

    ADMIN("管理员"),
    TEACHER("教师"),
    STUDENT("学生");

    private final String msg;

    UserTypeEnum(String msg) {
        this.msg = msg;
    }
}
