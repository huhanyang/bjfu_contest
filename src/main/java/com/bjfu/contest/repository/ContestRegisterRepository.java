package com.bjfu.contest.repository;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ContestRegisterRepository extends JpaRepository<ContestRegister, Long>, JpaSpecificationExecutor<ContestRegister> {

    Optional<ContestRegister> findByContestAndUserAndStatusIn(Contest contest, User user, List<ContestRegisterStatusEnum> statuses);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select register from ContestRegister register where register.contest=?1 and register.user=?2 and register.status in ?3")
    Optional<ContestRegister> findByContestAndUserAndStatusInForUpdate(Contest contest, User user, List<ContestRegisterStatusEnum> statuses);

    List<ContestRegister> findAllByUserAndStatusIn(User user, List<ContestRegisterStatusEnum> statuses);
}
