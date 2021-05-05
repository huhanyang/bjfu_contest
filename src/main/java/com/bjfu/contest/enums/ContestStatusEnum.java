package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 竞赛状态枚举
 * @author warthog
 */
@Getter
public enum ContestStatusEnum {

    CREATING("创建中"),
    REGISTERING("报名中"),
    RUNNING("进程中"),
    FINISH("结束"),
    DELETE("软删除");

    private final String msg;

    ContestStatusEnum(String msg) {
        this.msg = msg;
    }
}
