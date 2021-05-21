package com.bjfu.contest.dao;

import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestProcess;

import java.util.Optional;

public interface ContestProcessDAO {
    ContestProcess insert(ContestProcess process);
    void delete(ContestProcess process);
    ContestProcess update(ContestProcess process);
    Optional<ContestProcess> findById(Long id);
    Optional<ContestProcess> findByIdForUpdate(Long id);
    Optional<ContestProcess> findByContestAndSort(Contest contest, Integer sort);
}
