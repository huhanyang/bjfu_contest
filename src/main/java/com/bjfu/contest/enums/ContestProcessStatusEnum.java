package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 竞赛流程状态枚举
 * @author warthog
 */
@Getter
public enum ContestProcessStatusEnum {

    CREATING("创建中"),
    RUNNING("进程中"),
    FINISH("结束"),
    DELETE("软删除");

    private final String msg;

    ContestProcessStatusEnum(String msg) {
        this.msg = msg;
    }
}
