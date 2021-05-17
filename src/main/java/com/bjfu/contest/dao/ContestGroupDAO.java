package com.bjfu.contest.dao;

import com.bjfu.contest.pojo.entity.*;

import java.util.List;
import java.util.Optional;

public interface ContestGroupDAO {
    ContestGroup insert(ContestGroup group);
    void delete(ContestGroup group);
    ContestGroup update(ContestGroup group);
    Optional<ContestGroup> findById(Long id);
    Optional<ContestGroup> findByIdForUpdate(Long id);
    ContestProcessGroup addToProcess(ContestGroup group, ContestProcess process);
    ContestGroupMember addMember(Contest contest, ContestGroup group, User member);
    List<ContestGroup> findAllByContestAndMember(Contest contest, User member);
    List<ContestGroup> findAllByContestAndMemberForUpdate(Contest contest, User member);
    List<ContestGroup> findAllByContest(Contest contest);
    List<ContestGroup> findAllByMember(User user);
    List<ContestGroup> findAllByProcess(ContestProcess process);
    List<ContestGroup> findAllByProcessForUpdate(ContestProcess process);
}
