package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 报名表状态
 */
@Getter
public enum ContestRegisterStatusEnum {

    SIGN_UP("登记"),
    BAN("禁赛"),
    DELETE("删除");

    private final String msg;

    ContestRegisterStatusEnum(String msg) {
        this.msg = msg;
    }
}
