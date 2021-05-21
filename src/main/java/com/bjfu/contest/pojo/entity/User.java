package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.UserGenderEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 用户实体
 * @author warthog
 */
@Getter
@Setter
@Entity
public class User extends BaseEntity {
    /**
     * 账号
     */
    @Column(length=32, nullable=false)
    private String account;
    /**
     * 邮箱
     */
    @Column(length=32, nullable=false)
    private String email;
    /**
     * 密码
     */
    @Column(length=32, nullable=false)
    private String password;
    /**
     * 类型
     */
    @Enumerated
    @Column(nullable=false)
    private UserTypeEnum type;
    /**
     * 状态
     */
    @Enumerated
    @Column(nullable=false)
    private UserStatusEnum status;
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
     * 年级
     */
    @Column(length=32)
    private String grade;
    /**
     * 学院
     */
    @Column(length=32, nullable = false)
    private String college;
    /**
     * 专业
     */
    @Column(length=32)
    private String major;
    /**
     * 个人介绍
     */
    @Column(length=256)
    private String introduction;
    /**
     * 收件箱
     */
    @OneToMany(mappedBy = "user")
    private List<UserInbox> inboxes;

    // 教师相关
    /**
     * 主持的竞赛
     */
    @OneToMany(mappedBy = "creator")
    private List<Contest> creatorContests;
    /**
     * 当指导教师的竞赛
     */
    @OneToMany(mappedBy = "teacher")
    private List<ContestTeacher> teacherContests;
    /**
     * 指导的队伍
     */
    @OneToMany(mappedBy = "teacher")
    private List<ContestGroup> teacherGroups;

    // 学生相关
    /**
     * 报名的竞赛
     */
    @OneToMany(mappedBy = "user")
    private List<ContestRegister> contests;
    /**
     * 领导的队伍
     */
    @OneToMany(mappedBy = "captain")
    private List<ContestGroup> captainGroups;

}
