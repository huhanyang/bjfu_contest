package com.bjfu.contest.repository;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    Optional<Contest> findByIdAndStatusIn(Long id, List<ContestStatusEnum> statusEnums);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select contest from Contest contest where contest.id=?1 and contest.status in ?2")
    Optional<Contest> findByIdAndStatusInForUpdate(Long id, List<ContestStatusEnum> statusEnums);
}
