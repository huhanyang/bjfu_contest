package com.bjfu.contest.service;

import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.user.*;

/**
 * 用户相关操作服务
 * @author warthog
 */
public interface UserService {

    /**
     * 注册账号
     * @param request 请求
     * @return 新用户
     */
    UserDTO register(UserRegisterRequest request);

    /**
     * 激活账号
     * @param userId 用户id
     * @return 用户
     */
    UserDTO activate(Long userId);

    /**
     * 重新发送激活账号的邮件
     * @param account 用户账号
     */
    void sendActivateEmail(String account);

    /**
     * 登录检查
     * @param request 请求
     * @return 用户
     */
    UserDTO loginCheck(UserLoginCheckRequest request);

    /**
     * 修改自己的用户信息
     * @param request 请求
     * @param account 账号
     * @return 修改后的用户
     */
    UserDTO editSelfInfo(UserEditSelfInfoRequest request, String account);

    /**
     * 管理员修改用户信息
     * @param request 请求
     * @param userId 登录用户id
     * @return 修改后的用户
     */
    UserDTO editUserInfo(UserEditUserInfoRequest request, Long userId);

    /**
     * 修改密码
     * @param request 请求
     * @param account 账号
     */
    void changePassword(UserChangePasswordRequest request, String account);

    /**
     * 忘记密码
     * @param request 请求
     */
    void forgetPassword(UserForgetPasswordRequest request);

    /**
     * 设置为新密码
     * @param request 请求
     * @param userId 用户id
     */
    void resetPassword(UserResetPasswordRequest request, Long userId);

    /**
     * 获取用户信息
     * @param account 用户账号
     * @return 用户信息
     */
    UserDTO getUserInfo(String account);

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UserDTO getUserInfo(Long userId);

}
