package com.bjfu.contest.pojo.request.user;

import com.bjfu.contest.enums.UserGenderEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequest {
    /**
     * 学工号
     */
    @NotBlank(message = "学工号不能为空!")
    @Length(min = 8, max = 32, message = "学工号长度在8-32位!")
    private String account;
    /**
     * 邮箱
     */
    @Email
    @NotBlank(message = "邮箱不能为空!")
    @Length(max = 32, message = "邮箱长度最大32位!")
    private String email;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    @Length(min = 8, max = 32, message = "密码长度在8-32位!")
    private String password;
    /**
     * 账号类型
     */
    @NotNull(message = "账号类型不能为空")
    private UserTypeEnum type;
    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空!")
    @Length(min = 1, max = 32, message = "真实姓名长度1到32个字符!")
    private String name;
    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private UserGenderEnum gender;
    /**
     * 年级(可选)
     */
    @Length(min = 4, max = 4, message = "年级长度4位!")
    private String grade;
    /**
     * 学院
     */
    @NotBlank(message = "学院不能为空!")
    @Length(max = 32, message = "学院长度最大32位!")
    private String college;
    /**
     * 专业(可选)
     */
    @Length(max = 32, message = "专业长度最大32位!")
    private String major;
    /**
     * 个人介绍(可选)
     */
    @Length(max = 256, message = "个人介绍最多256个字!")
    private String introduction;

}
