package com.bjfu.contest.utils;

import com.bjfu.contest.pojo.dto.UserDTO;

import java.util.Optional;

/**
 * 用户上下文工具类
 * @author warthog
 */
public class UserInfoContextUtil {

    private static final ThreadLocal<UserDTO> userInfoThreadLocal = new ThreadLocal<>();

    public static Optional<UserDTO> getUserInfo() {
        return Optional.ofNullable(userInfoThreadLocal.get());
    }

    public static void setUserInfo(UserDTO userDTO) {
        userInfoThreadLocal.set(userDTO);
    }

    public static void clear() {
        userInfoThreadLocal.remove();
    }

}
