package com.bjfu.contest.pojo.request.user;

import com.bjfu.contest.enums.UserGenderEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Enumerated;

@Data
public class UserEditSelfInfoRequest {
    /**
     * 姓名
     */
    @Column(length=32, nullable=false)
    private String name;
    /**
     * 性别
     */
    @Enumerated
    @Column(nullable=false)
    private UserGenderEnum gender;
    /**
     * 个人介绍
     */
    @Column(length=256)
    private String introduction;
}
