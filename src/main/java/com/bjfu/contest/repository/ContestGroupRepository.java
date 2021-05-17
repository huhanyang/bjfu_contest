package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ContestGroupRepository extends JpaRepository<ContestGroup, Long> {

    Optional<ContestGroup> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select contestGroup from ContestGroup contestGroup where contestGroup.id=?1")
    Optional<ContestGroup> findByIdForUpdate(Long id);

    List<ContestGroup> findAllByContest(Contest contest);
}
