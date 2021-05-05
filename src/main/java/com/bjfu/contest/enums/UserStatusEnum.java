package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 用户账号状态枚举
 * @author warthog
 */
@Getter
public enum UserStatusEnum {

    UNACTIVE("未激活"),
    ACTIVE("活跃"),
    BANNED("封禁"),
    DELETE("删除");

    private final String msg;

    UserStatusEnum(String msg) {
        this.msg = msg;
    }
}
