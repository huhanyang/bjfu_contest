package com.bjfu.contest.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(100, "成功"),

    NEED_TO_LOGIN(101, "需要登录"),
    ACCOUNT_BANNED(102, "账号已被封禁"),
    REQUIRE_ADMIN(103, "仅管理员可访问"),
    REQUIRE_TEACHER(104, "仅教师可访问"),
    REQUIRE_STUDENT(105, "仅学生可访问"),


    WRONG_REQUEST_PARAMS(200, "请求参数错误"),
    ACCOUNT_REGISTERED(201, "账号已经注册"),
    EMAIL_REGISTERED(202, "邮箱已经注册"),
    USER_DONT_EXIST(203, "用户不存在"),
    USER_IS_BANNED(204, "用户已被封禁"),
    USER_IS_ACTIVE(205, "用户已经激活"),
    USER_IS_UNACTIVE(206, "用户还未激活"),
    PASSWORD_WRONG(207, "密码错误"),
    TOKEN_GENERATE_FAILED(208, "token生成失败"),
    TOKEN_WRONG(209, "token错误或过期"),
    ACCOUNT_HAS_RECYCLED(210, "长时间未激活，账号已被回收"),
    CONTEST_NOT_EXIST(211, "竞赛不存在"),
    NOT_CONTEST_CREATOR(212, "非竞赛创建人"),
    EXIST_PROCESS_NOT_FINISH(213, "存在流程没有完成"),
    REGISTER_PROCESS_NOT_EXIST(214, "竞赛报名流程不存在"),
    PROCESS_NOT_EXIST(214, "竞赛流程不存在"),
    PROCESS_NOT_RUNNING(214, "竞赛流程非运行状态"),
    PROCESS_FINISHED(215, "竞赛流程已结束"),
    PROCESS_NOT_CREATING(216, "竞赛流程非创建状态"),
    REGISTER_PROCESS_NOT_RUNNING(226, "竞赛报名流程非运行状态"),
    USER_REGISTERED(217, "用户已经报名过"),
    CONTEST_NOT_REGISTERING(218, "竞赛非报名状态"),
    USER_NOT_REGISTERED(219, "未曾报名竞赛"),
    TEACHER_REGISTERED(220, "教师已经是指导教师"),
    TEACHER_NOT_REGISTERED(220, "教师不是指导教师"),
    HAS_JOINED_GROUP(221, "已经加入过其他队伍"),
    GROUP_NOT_EXIST(222, "队伍不存在"),
    NOT_GROUP_CAPTAIN(223, "非队长不可修改"),

    USER_CONTEXT_ERROR(301, "用户信息登录上下文出错"),

    ;

    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
