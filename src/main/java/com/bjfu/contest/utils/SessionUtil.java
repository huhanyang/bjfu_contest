package com.bjfu.contest.utils;

import javax.servlet.http.HttpSession;

/**
 * Session工具类
 * @author warthog
 */
public class SessionUtil {
    public static String getUserAccount(HttpSession session) {
        return (String) session.getAttribute("account");
    }
}
