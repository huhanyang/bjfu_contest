package com.bjfu.contest.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static String getUserAccount(HttpSession session) {
        return (String) session.getAttribute("account");
    }
}
