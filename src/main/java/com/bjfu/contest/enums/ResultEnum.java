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
    PROCESS_NOT_LAST(216, "竞赛流程非最后一个"),
    REGISTER_PROCESS_NOT_RUNNING(226, "竞赛报名流程非运行状态"),
    USER_REGISTERED(217, "用户已经报名过"),
    CONTEST_NOT_REGISTERING(218, "竞赛非报名状态"),
    CONTEST_NOT_CREATING(218, "竞赛非创建状态"),
    CONTEST_FINISH(218, "竞赛已经结束"),
    USER_NOT_REGISTERED(219, "未曾报名竞赛"),
    TEACHER_REGISTERED(220, "教师已经是指导教师"),
    TEACHER_NOT_REGISTERED(220, "教师不是指导教师"),
    HAS_JOINED_GROUP(221, "已经加入过其他队伍"),
    GROUP_NOT_EXIST(222, "队伍不存在"),
    NOT_GROUP_CAPTAIN(223, "非队长不可修改"),
    CONTEST_STATUS_CHANGE_NOT_ALLOWED(224, "竞赛状态变更不允许"),
    GROUP_TEACHER_EXIST(222, "竞赛队伍存在指导教师"),
    NOT_GROUP_TEACHER(222, "竞赛队伍存在指导教师"),
    GROUP_CAPTAIN_CAN_NOT_KICK(222, "队伍队长不能被踢出"),
    RESOURCE_NOT_EXIST(222, "资源不存在"),
    NOT_RESOURCE_CREATOR(222, "非资源创建人"),
    CANT_ACCESS_RESOURCE(222, "不能访问此资源"),

    USER_CONTEXT_ERROR(301, "用户信息登录上下文出错"),
    PROCESS_SORT_ERROR(302, "流程序号错误"),
    PROCESS_BEFORE_STATUS_ERROR(302, "上一个流程的状态错误"),

    OSS_CLIENT_INIT_FAILED(401, "OSS客户端初始化失败"),
    FILE_UPLOAD_FAILED(402, "文件上传失败"),
    FILE_DOWNLOAD_FAILED(403, "文件下载失败"),
    FILE_DELETE_FAILED(403, "文件删除失败"),
    GET_FILE_INPUT_STREAM_FAILED(404, "获取文件输入流失败"),
    ;

    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
