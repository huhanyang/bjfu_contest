package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 通知类型枚举
 * @author warthog
 */
@Getter
public enum NotifyTypeEnum {

    ALL_NOTIFY("首页通知"),
    USERS_NOTIFY("全体用户"),
    STUDENTS_NOTIFY("全体学生"),
    TEACHERS_NOTIFY("全体教师"),
    CONTEST_NOTIFY("竞赛内通知"),
    CONTEST_PROCESS_NOTIFY("竞赛流程内通知"),
    GROUP_NOTIFY("队伍内通知");

    private final String msg;

    NotifyTypeEnum(String msg) {
        this.msg = msg;
    }
}
