package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 资源类型枚举
 * @author warthog
 */
@Getter
public enum ResourceTypeEnum {

    ALL("首页资源"),
    USER("所有用户的资源"),
    TEACHER("面向教师的资源"),
    STUDENT("面向学生的资源"),
    CONTEST("面向竞赛的资源"),
    PROCESS("面向当前流程的资源"),
    GROUP("面向队伍的资源");

    private final String msg;

    ResourceTypeEnum(String msg) {
        this.msg = msg;
    }
}
