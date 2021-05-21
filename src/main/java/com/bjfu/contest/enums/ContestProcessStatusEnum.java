package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 竞赛流程状态枚举
 * @author warthog
 */
@Getter
public enum ContestProcessStatusEnum {

    CREATING("创建中", 0),
    RUNNING("进程中", 1),
    FINISH("结束", 2),
    DELETE("软删除", 3);

    private final String msg;
    private final Integer weight;

    ContestProcessStatusEnum(String msg, Integer weight) {
        this.msg = msg;
        this.weight = weight;
    }
}
