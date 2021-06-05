package com.bjfu.contest.security;

import com.auth0.jwt.interfaces.Claim;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.security.annotation.RequireAdmin;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.security.annotation.RequireStudent;
import com.bjfu.contest.security.annotation.RequireTeacher;
import com.bjfu.contest.service.UserService;
import com.bjfu.contest.utils.JwtUtil;
import com.bjfu.contest.utils.ResponseUtil;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 用户信息拦截器
 * @author warthog
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    /**
     * 进入controller前检查权限
     * @param request http请求
     * @param response http响应
     * @param handler 处理器
     * @return 是否可以通过拦截器
     * @throws Exception 拦截器出现异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isHandlerMethod = handler.getClass().isAssignableFrom(HandlerMethod.class);
        if(isHandlerMethod) {
            HandlerMethod handlerMethod= ((HandlerMethod)handler);
            // 获取接口上的访问权限控制注解
            boolean requireLogin = (handlerMethod.getMethodAnnotation(RequireLogin.class) != null);
            boolean requireStudent = handlerMethod.getMethodAnnotation(RequireStudent.class) != null;
            boolean requireTeacher = handlerMethod.getMethodAnnotation(RequireTeacher.class) != null;
            boolean requireAdmin = handlerMethod.getMethodAnnotation(RequireAdmin.class) != null;
            requireLogin = requireLogin || requireAdmin || requireStudent || requireTeacher;
            // 尝试从header中取token 取不到就从http参数中取
            String token = Optional.ofNullable(request.getHeader("Authorization"))
                    .filter(tokenInHeader -> tokenInHeader.length() > 7)
                    .map(tokenInHeader -> tokenInHeader.substring(7)) // 前缀"Bearer "清除
                    .orElse(request.getParameter("token"));
            // 尝试验证解析token 并 获取用户登录信息
            UserDTO userInfo = Optional.ofNullable(token)
                    .map(JwtUtil::verifyToken)
                    .map(claimMap -> claimMap.get("userId"))
                    .map(Claim::asString)
                    .map(Long::valueOf)
                    .map(userId -> userService.getUserInfo(userId))
                    .orElse(null);
            // 需要登录则检查是否登录以及是否被封号
            if(requireLogin) {
                if(userInfo == null) {
                    ResponseUtil.writeResultToResponse(ResultEnum.NEED_TO_LOGIN, response);
                    return false;
                }
                if(UserStatusEnum.BANNED.equals(userInfo.getStatus())){
                    ResponseUtil.writeResultToResponse(ResultEnum.ACCOUNT_BANNED, response);
                    return false;
                }
                UserInfoContextUtil.setUserInfo(userInfo);
            }
            // 判断是否为管理员
            if(requireAdmin) {
                if(!userInfo.getType().equals(UserTypeEnum.ADMIN)) {
                    ResponseUtil.writeResultToResponse(ResultEnum.REQUIRE_ADMIN, response);
                    return false;
                }
            }
            // 判断是否为教师
            if(requireTeacher) {
                if(!userInfo.getType().equals(UserTypeEnum.TEACHER)) {
                    ResponseUtil.writeResultToResponse(ResultEnum.REQUIRE_TEACHER, response);
                    return false;
                }
            }
            // 判断是否为学生
            if(requireStudent) {
                if(!userInfo.getType().equals(UserTypeEnum.STUDENT)) {
                    ResponseUtil.writeResultToResponse(ResultEnum.REQUIRE_STUDENT, response);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 清空上下文中的用户信息
        UserInfoContextUtil.clear();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
