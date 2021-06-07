package com.bjfu.contest.enums;

import lombok.Getter;

/**
 * 资源内容类型枚举
 */
@Getter
public enum ResourceContentTypeEnum {

    RICH_TEXT("富文本"),
    OTHER("其他类型");

    private final String msg;

    ResourceContentTypeEnum(String msg) {
        this.msg = msg;
    }
}
