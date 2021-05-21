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
    List<ContestProcessGroup> addAllToProcess(ContestProcess process, List<ContestGroup> groups);
    ContestGroupMember addMember(Contest contest, ContestGroup group, ContestRegister member);
    void deleteMember(Contest contest, ContestGroup group, ContestRegister member);
    List<ContestGroup> findAllByContestAndMember(Contest contest, ContestRegister member);
    List<ContestGroup> findAllByContestAndMemberForUpdate(Contest contest, ContestRegister member);
    List<ContestGroup> findAllByContest(Contest contest);
    List<ContestGroup> findAllByMember(User user);
    List<ContestGroup> findAllByProcess(ContestProcess process);
    List<ContestGroup> findAllByProcessForUpdate(ContestProcess process);
    List<ContestGroup> findAllByProcessAndIdInForUpdate(ContestProcess process, List<Long> groupIds);
    List<ContestGroup> findAllByProcessAndGroupNotInForUpdate(ContestProcess process, List<ContestGroup> groups);
    void deleteGroupsInProcessByProcessAndGroupIdIn(ContestProcess process, List<Long> groupIds);
}
