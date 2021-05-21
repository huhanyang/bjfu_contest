package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 竞赛状态枚举
 * @author warthog
 */
@Getter
public enum ContestStatusEnum {

    CREATING("创建中", 0),
    REGISTERING("报名中", 1),
    RUNNING("进程中", 2),
    FINISH("结束", 3),
    DELETE("软删除", 4);

    private final String msg;
    private final Integer weight;

    ContestStatusEnum(String msg, Integer weight) {
        this.msg = msg;
        this.weight = weight;
    }
}
