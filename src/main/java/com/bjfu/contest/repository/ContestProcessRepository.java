package com.bjfu.contest.repository;

import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ContestProcessRepository extends JpaRepository<ContestProcess, Long> {

    Optional<ContestProcess> findByIdAndStatusIn(Long id, List<ContestProcessStatusEnum> statuses);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select process from ContestProcess process where process.id=?1 and process.status in ?2")
    Optional<ContestProcess> findByIdAndStatusInForUpdate(Long id, List<ContestProcessStatusEnum> statuses);

    Optional<ContestProcess> findByContestAndSortAndStatusIn(Contest contest, Integer id, List<ContestProcessStatusEnum> statuses);
}
