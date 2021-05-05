package com.bjfu.contest.enums;

import lombok.Getter;

@Getter
public enum ContestGroupStatusEnum {

    ACTIVE("流程中"),
    FINISH("流程结束"),
    DELETE("删除");

    private final String msg;
    ContestGroupStatusEnum(String msg) {
        this.msg = msg;
    }
}
