package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 资源操作类型枚举
 * @author warthog
 */
@Getter
public enum ResourceOperateTypeEnum {

    UPLOAD("上传"),
    DELETE("删除"),
    EDIT("编辑"),
    LIST("拉取"),
    DOWNLOAD("下载");

    private final String msg;

    ResourceOperateTypeEnum(String msg) {
        this.msg =msg;
    }
}
