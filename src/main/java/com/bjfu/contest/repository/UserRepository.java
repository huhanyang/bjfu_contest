package com.bjfu.contest.repository;

import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndStatusIn(Long id, List<UserStatusEnum> statusEnums);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select user from User user where user.id=?1 and user.status in ?2")
    Optional<User> findByIdAndStatusInForUpdate(Long id, List<UserStatusEnum> statusEnums);

    List<User> findAllByAccountAndStatusIn(String account, List<UserStatusEnum> statusEnums);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select user from User user where user.account=?1 and user.status in ?2")
    List<User> findAllByAccountAndStatusInForUpdate(String account, List<UserStatusEnum> statusEnums);

    List<User> findAllByEmailAndStatusIn(String email, List<UserStatusEnum> statusEnums);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select user from User user where user.email=?1 and user.status in ?2")
    List<User> findAllByEmailAndStatusInForUpdate(String email, List<UserStatusEnum> statusEnums);

}
