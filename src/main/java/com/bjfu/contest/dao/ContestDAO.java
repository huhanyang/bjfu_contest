package com.bjfu.contest.dao;

import com.bjfu.contest.pojo.entity.Contest;

import java.util.Optional;

public interface ContestDAO {
    Contest insert(Contest contest);
    void delete(Contest contest);
    Contest update(Contest contest);
    Optional<Contest> findById(Long id);
    Optional<Contest> findByIdForUpdate(Long id);
}
