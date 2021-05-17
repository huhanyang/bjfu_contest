package com.bjfu.contest.dao;

import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.pojo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User insert(User user);
    void delete(User user);
    User update(User user);
    Optional<User> findById(Long id);
    Optional<User> findByIdForUpdate(Long id);
    Optional<User> findByAccount(String account);
    Optional<User> findActiveUserByAccount(String account);
    List<User> findAllByAccountIn(List<String> accounts);
    Optional<User> findByAccountForUpdate(String account);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailForUpdate(String email);
    List<User> findByNameLikeAndTypeIn(String name, List<UserTypeEnum> types);
}
