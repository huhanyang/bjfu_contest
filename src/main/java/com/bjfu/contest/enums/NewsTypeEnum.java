package com.bjfu.contest.enums;

import lombok.Getter;

@Getter
public enum NewsTypeEnum {

    FRONT_NEWS("首页通知"),
    BACK_NEWS("后台通知"),
    CONTEST_NEWS("竞赛内"),
    CONTEST_PROCESS_NEWS("竞赛流程内"),
    GROUP_NEWS("队伍内");

    private final String msg;

    NewsTypeEnum(String msg) {
        this.msg = msg;
    }

}
