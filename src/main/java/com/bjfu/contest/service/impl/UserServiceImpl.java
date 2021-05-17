package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.user.*;
import com.bjfu.contest.service.UserService;
import com.bjfu.contest.utils.EncryptionUtil;
import com.bjfu.contest.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void register(UserRegisterRequest request) {
        if(request.getType().equals(UserTypeEnum.ADMIN)) {
            throw new BizException(ResultEnum.WRONG_REQUEST_PARAMS);
        }
        String account = request.getAccount();
        String email = request.getEmail();
        User user1 = userDAO.findByAccountForUpdate(account).orElse(null);
        if(user1 != null) {
            if(checkUnActiveUserIsExpire(user1)) {
                //清理掉长时间未激活的账号
                userDAO.delete(user1);
            } else {
                //存在注册过的账号
                throw new BizException(ResultEnum.ACCOUNT_REGISTERED);
            }
        }
        User user2 = userDAO.findByEmailForUpdate(email).orElse(null);
        if(user2 != null) {
            if(checkUnActiveUserIsExpire(user2)) {
                //清理掉长时间未激活的账号
                userDAO.delete(user2);
            } else {
                //存在注册过的邮箱
                throw new BizException(ResultEnum.EMAIL_REGISTERED);
            }
        }
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(EncryptionUtil.md5Encode(request.getPassword()));
        userDAO.insert(user);
        // todo 发激活邮件
        String token = JwtUtil.generateToken(Collections.singletonMap("userId", user.getId().toString()));
        log.error("新注册账号: token={}", token);
    }

    /**
     * 检查用户激活时间是否过期(7天有效期)
     * @param user 用户
     * @return true过期
     */
    private boolean checkUnActiveUserIsExpire(User user) {
        if(user.getStatus().equals(UserStatusEnum.UNACTIVE)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getCreatedTime());
            calendar.add(Calendar.DATE, 7);
            return new Date().after(calendar.getTime());
        }
        return false;
    }

    @Override
    @Transactional
    public UserDTO activate(Long userId) {
        User user = userDAO.findByIdForUpdate(userId)
                .orElseThrow(() -> new BizException(ResultEnum.ACCOUNT_HAS_RECYCLED));
        if(!user.getStatus().equals(UserStatusEnum.UNACTIVE)) {
            // 已注册就失效掉激活功能
            throw new BizException(ResultEnum.USER_IS_ACTIVE);
        }
        user.setStatus(UserStatusEnum.ACTIVE);
        userDAO.update(user);
        return new UserDTO(user);
    }

    @Override
    public void sendActivateEmail(String account) {
        User user = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        if(!user.getStatus().equals(UserStatusEnum.UNACTIVE)) {
            // 已注册就失效掉激活功能
            throw new BizException(ResultEnum.USER_IS_ACTIVE);
        }
        String token = JwtUtil.generateToken(Collections.singletonMap("userId", user.getId().toString()));
        log.error("重新发送激活账号邮件: token={}", token);
        // todo 发送注册激活邮件
    }

    @Override
    public UserDTO loginCheck(UserLoginCheckRequest request) {
        User user = userDAO.findByAccount(request.getAccount())
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        if(user.getStatus().equals(UserStatusEnum.BANNED)) {
            throw new BizException(ResultEnum.USER_IS_BANNED);
        }
        if(!user.getPassword().equals(EncryptionUtil.md5Encode(request.getPassword()))) {
            throw new BizException(ResultEnum.PASSWORD_WRONG);
        }
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public void editSelfInfo(UserEditSelfInfoRequest request, String account) {
        User user = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        if(user.getStatus().equals(UserStatusEnum.BANNED)) {
            throw new BizException(ResultEnum.USER_IS_BANNED);
        }
        BeanUtils.copyProperties(request, user);
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void editUserInfo(UserEditUserInfoRequest request, Long userId) {
        userDAO.findById(userId)
                .filter(user -> user.getType().equals(UserTypeEnum.ADMIN))
                .or(()->{throw new BizException(ResultEnum.REQUIRE_ADMIN);});
        User user = userDAO.findByIdForUpdate(request.getUserId())
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        User user1 = userDAO.findByAccountForUpdate(request.getAccount())
                .filter(u -> !u.getId().equals(request.getUserId()))
                .orElse(null);
        if(user1 != null) {
            if(checkUnActiveUserIsExpire(user1)) {
                //清理掉长时间未激活的账号
                userDAO.delete(user1);
            } else {
                //存在注册过的账号
                throw new BizException(ResultEnum.ACCOUNT_REGISTERED);
            }
        }
        User user2 = userDAO.findByEmailForUpdate(request.getEmail())
                .filter(u -> !u.getId().equals(request.getUserId()))
                .orElse(null);
        if(user2 != null) {
            if(checkUnActiveUserIsExpire(user2)) {
                //清理掉长时间未激活的账号
                userDAO.delete(user2);
            } else {
                //存在注册过的邮箱
                throw new BizException(ResultEnum.EMAIL_REGISTERED);
            }
        }
        BeanUtils.copyProperties(request, user);
        userDAO.update(user);
    }

    @Override
    public void changePassword(UserChangePasswordRequest request, String account) {
        User user = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        if(user.getStatus().equals(UserStatusEnum.BANNED)) {
            throw new BizException(ResultEnum.USER_IS_BANNED);
        }
        if(!user.getPassword().equals(EncryptionUtil.md5Encode(request.getOldPassword()))) {
            throw new BizException(ResultEnum.PASSWORD_WRONG);
        }
        user.setPassword(EncryptionUtil.md5Encode(request.getNewPassword()));
        userDAO.update(user);
    }

    @Override
    public void forgetPassword(UserForgetPasswordRequest request) {
        User user = userDAO.findByEmail(request.getEmail())
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        if(user.getStatus().equals(UserStatusEnum.BANNED)) {
            throw new BizException(ResultEnum.USER_IS_BANNED);
        }
        // todo 生成忘记密码邮件
        String token = JwtUtil.generateToken(Collections.singletonMap("userId", user.getId().toString()));
        log.error("忘记密码: token={}", token);
    }

    @Override
    @Transactional
    public void resetPassword(UserResetPasswordRequest request, Long userId) {
        User user = userDAO.findByIdForUpdate(userId)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        if(user.getStatus().equals(UserStatusEnum.BANNED)) {
            throw new BizException(ResultEnum.USER_IS_BANNED);
        }
        user.setPassword(EncryptionUtil.md5Encode(request.getPassword()));
        userDAO.update(user);
    }

    @Override
    public UserDTO getMyInfo(Long userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        return new UserDTO(user, true, true, true, true,
                true, true, true);
    }

    @Override
    public UserDTO getUserInfo(Long userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> searchByNameAndType(UserSearchRequest request) {
        return userDAO.findByNameLikeAndTypeIn("%"+request.getName()+"%", request.getTypes())
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
}
