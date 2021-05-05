package com.bjfu.contest.dao;

import com.bjfu.contest.pojo.entity.User;

import java.util.Optional;

public interface UserDAO {
    User insert(User user);
    void delete(User user);
    User update(User user);
    Optional<User> findById(Long id);
    Optional<User> findByIdForUpdate(Long id);
    Optional<User> findByAccount(String account);
    Optional<User> findByAccountForUpdate(String account);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailForUpdate(String email);
}
