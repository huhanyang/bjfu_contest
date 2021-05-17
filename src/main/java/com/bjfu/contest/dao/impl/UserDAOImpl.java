package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    private static final List<UserStatusEnum> REGISTERED_STATUS_ENUMS = new ArrayList<>();

    static {
        REGISTERED_STATUS_ENUMS.add(UserStatusEnum.UNACTIVE);
        REGISTERED_STATUS_ENUMS.add(UserStatusEnum.ACTIVE);
        REGISTERED_STATUS_ENUMS.add(UserStatusEnum.BANNED);
    }


    @Override
    public User insert(User user) {
        user.setId(null);
        user.setStatus(UserStatusEnum.UNACTIVE);
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        user.setStatus(UserStatusEnum.DELETE);
        userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findByIdAndStatusIn(id, REGISTERED_STATUS_ENUMS);
    }

    @Override
    public Optional<User> findByIdForUpdate(Long id) {
        return userRepository.findByIdAndStatusInForUpdate(id, REGISTERED_STATUS_ENUMS);
    }

    @Override
    public Optional<User> findByAccount(String account) {
        return userRepository.findAllByAccountAndStatusIn(account, REGISTERED_STATUS_ENUMS)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findActiveUserByAccount(String account) {
        return userRepository.findAllByAccountAndStatusIn(account, Collections.singletonList(UserStatusEnum.ACTIVE))
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findAllByAccountIn(List<String> accounts) {
        return userRepository.findAllByAccountInAndStatusIn(accounts, Collections.singletonList(UserStatusEnum.ACTIVE));
    }

    @Override
    public Optional<User>  findByAccountForUpdate(String account) {
        return userRepository.findAllByAccountAndStatusInForUpdate(account, REGISTERED_STATUS_ENUMS)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findAllByEmailAndStatusIn(email, REGISTERED_STATUS_ENUMS)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findByEmailForUpdate(String email) {
        return userRepository.findAllByEmailAndStatusInForUpdate(email, REGISTERED_STATUS_ENUMS)
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findByNameLikeAndTypeIn(String name, List<UserTypeEnum> types) {
        return userRepository.findByNameLikeAndStatusInAndTypeIn(name, Collections.singletonList(UserStatusEnum.ACTIVE), types);
    }
}
