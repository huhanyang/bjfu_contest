package com.bjfu.contest.enums;

/**
 * 用户性别枚举
 * @author warthog
 */
public enum UserGenderEnum {

    MALE("男性"),
    FEMALE("女性"),
    SECRECY("保密");

    private final String msg;

    UserGenderEnum(String msg) {
        this.msg = msg;
    }
}
