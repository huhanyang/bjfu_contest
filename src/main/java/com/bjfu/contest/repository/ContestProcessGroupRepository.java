package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.ContestGroup;
import com.bjfu.contest.pojo.entity.ContestProcess;
import com.bjfu.contest.pojo.entity.ContestProcessGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;

public interface ContestProcessGroupRepository extends JpaRepository<ContestProcessGroup, Long> {
    List<ContestProcessGroup> findAllByProcess(ContestProcess process);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select processGroup from ContestProcessGroup processGroup where processGroup.process=?1")
    List<ContestProcessGroup> findAllByProcessForUpdate(ContestProcess process);
}
