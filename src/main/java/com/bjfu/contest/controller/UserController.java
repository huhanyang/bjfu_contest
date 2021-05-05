package com.bjfu.contest.controller;

import com.auth0.jwt.interfaces.Claim;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.exception.AppException;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.user.*;
import com.bjfu.contest.pojo.vo.UserVO;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.service.UserService;
import com.bjfu.contest.utils.JwtUtil;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResult<UserVO> login(@Validated @RequestBody UserLoginCheckRequest request) {
        UserDTO userDTO = userService.loginCheck(request);
        String token = JwtUtil.generateToken(Collections.singletonMap("userId", userDTO.getId().toString()));
        return BaseResult.success(new UserVO(userDTO, token));
    }

    @PostMapping("/register")
    public BaseResult<Void> register(@Validated @RequestBody UserRegisterRequest request) {
        UserDTO userDTO = userService.register(request);
        return BaseResult.success();
    }

    @PostMapping("/activate")
    public BaseResult<UserVO> activate(@Validated @RequestBody UserActivateRequest request) {
        Map<String, Claim> claimMap = JwtUtil.verifyToken(request.getToken());
        Long userId = Optional.ofNullable(claimMap.get("userId"))
                .map(Claim::asString)
                .map(Long::valueOf)
                .orElseThrow(() -> new BizException(ResultEnum.TOKEN_WRONG));
        UserDTO userDTO = userService.activate(userId);
        String token = JwtUtil.generateToken(Collections.singletonMap("userId", userDTO.getId().toString()));
        return BaseResult.success(new UserVO(userDTO, token));
    }

    @PostMapping("/sendActivateEmail")
    public BaseResult<Void> sendActivateEmail() {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        userService.sendActivateEmail(userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @PostMapping("/editUserInfo")
    public BaseResult<UserVO> editUserInfo(@Validated @RequestBody UserEditUserInfoRequest request) {
        UserDTO self = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        UserDTO userDTO = userService.editUserInfo(request, self.getId());
        return BaseResult.success(new UserVO(userDTO, null));
    }

    @RequireLogin
    @PostMapping("/editSelfInfo")
    public BaseResult<UserVO> editSelfInfo(@Validated @RequestBody UserEditSelfInfoRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        userService.editSelfInfo(request, userDTO.getAccount());
        return BaseResult.success(new UserVO(userDTO, null));
    }

    @RequireLogin
    @PostMapping("/changePassword")
    public BaseResult<Void> changePassword(@Validated @RequestBody UserChangePasswordRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        userService.changePassword(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @PostMapping("/forgetPassword")
    public BaseResult<Void> forgetPassword(@Validated @RequestBody UserForgetPasswordRequest request) {
        userService.forgetPassword(request);
        return BaseResult.success();
    }

    @PostMapping("/resetPassword")
    public BaseResult<Void> resetPassword(@Validated @RequestBody UserResetPasswordRequest request) {
        Map<String, Claim> claimMap = JwtUtil.verifyToken(request.getToken());
        Long userId = Optional.ofNullable(claimMap.get("userId"))
                .map(Claim::asString)
                .map(Long::valueOf)
                .orElseThrow(() -> new BizException(ResultEnum.TOKEN_WRONG));
        userService.resetPassword(request, userId);
        return BaseResult.success();
    }

    @RequireLogin
    @GetMapping("/me")
    public BaseResult<UserVO> me() {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        userDTO = userService.getUserInfo(userDTO.getAccount());
        if(userDTO.getStatus().equals(UserStatusEnum.BANNED)) {
            throw new BizException(ResultEnum.USER_IS_BANNED);
        }
        String token = JwtUtil.generateToken(Collections.singletonMap("userId", userDTO.getId().toString()));
        return BaseResult.success(new UserVO(userDTO, token));
    }

    @RequireLogin
    @GetMapping("/getUserInfo")
    public BaseResult<UserVO> getUserInfo(Long userId) {
        UserDTO userDTO = userService.getUserInfo(userId);
        return BaseResult.success(new UserVO(userDTO, null));
    }

}
