package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.ContestGroupDAO;
import com.bjfu.contest.enums.ContestProcessGroupStatusEnum;
import com.bjfu.contest.pojo.entity.*;
import com.bjfu.contest.repository.ContestGroupMemberRepository;
import com.bjfu.contest.repository.ContestGroupRepository;
import com.bjfu.contest.repository.ContestProcessGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContestGroupDAOImpl implements ContestGroupDAO {

    @Autowired
    private ContestGroupRepository contestGroupRepository;
    @Autowired
    private ContestGroupMemberRepository contestGroupMemberRepository;
    @Autowired
    private ContestProcessGroupRepository contestProcessGroupRepository;

    @Override
    public ContestGroup insert(ContestGroup group) {
        return contestGroupRepository.save(group);
    }

    @Override
    public ContestProcessGroup addToProcess(ContestGroup group, ContestProcess process) {
        ContestProcessGroup processGroup = new ContestProcessGroup();
        processGroup.setGroup(group);
        processGroup.setProcess(process);
        processGroup.setStatus(ContestProcessGroupStatusEnum.PREPARING);
        contestProcessGroupRepository.save(processGroup);
        return processGroup;
    }

    @Override
    public List<ContestProcessGroup> addAllToProcess(ContestProcess process, List<ContestGroup> groups) {
        List<ContestProcessGroup> processGroups = groups.stream().map(group -> {
            ContestProcessGroup processGroup = new ContestProcessGroup();
            processGroup.setProcess(process);
            processGroup.setGroup(group);
            processGroup.setStatus(ContestProcessGroupStatusEnum.PREPARING);
            return processGroup;
        }).collect(Collectors.toList());
        contestProcessGroupRepository.saveAll(processGroups);
        return processGroups;
    }

    @Override
    public ContestGroupMember addMember(Contest contest, ContestGroup group, ContestRegister member) {
        ContestGroupMember groupMember = new ContestGroupMember();
        groupMember.setContest(contest);
        groupMember.setGroup(group);
        groupMember.setMember(member);
        contestGroupMemberRepository.save(groupMember);
        return groupMember;
    }

    @Override
    public void deleteMember(Contest contest, ContestGroup group, ContestRegister member) {
        contestGroupMemberRepository.deleteByContestAndGroupAndMember(contest, group, member);
    }

    @Override
    public void delete(ContestGroup group) {
        contestGroupMemberRepository.deleteAll(group.getMembers());
        contestProcessGroupRepository.deleteAll(group.getProcesses());
        contestGroupRepository.delete(group);
    }

    @Override
    public ContestGroup update(ContestGroup group) {
        return contestGroupRepository.save(group);
    }

    @Override
    public Optional<ContestGroup> findById(Long id) {
        return contestGroupRepository.findById(id);
    }

    @Override
    public Optional<ContestGroup> findByIdForUpdate(Long id) {
        return contestGroupRepository.findByIdForUpdate(id);
    }

    @Override
    public List<ContestGroup> findAllByContestAndMember(Contest contest, ContestRegister member) {
        return contestGroupMemberRepository.findAllByContestAndMember(contest, member)
                .stream()
                .map(ContestGroupMember::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroup> findAllByContestAndMemberForUpdate(Contest contest, ContestRegister member) {
        return contestGroupMemberRepository.findAllByContestAndMemberForUpdate(contest, member)
                .stream()
                .map(ContestGroupMember::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroup> findAllByContest(Contest contest) {
        return contestGroupRepository.findAllByContest(contest);
    }

    @Override
    public List<ContestGroup> findAllByMember(User user) {
        return contestGroupMemberRepository.findAllByMember(user)
                .stream()
                .map(ContestGroupMember::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroup> findAllByProcess(ContestProcess process) {
        return contestProcessGroupRepository.findAllByProcess(process)
                .stream()
                .map(ContestProcessGroup::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroup> findAllByProcessForUpdate(ContestProcess process) {
        return contestProcessGroupRepository.findAllByProcessForUpdate(process)
                .stream()
                .map(ContestProcessGroup::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroup> findAllByProcessAndIdInForUpdate(ContestProcess process, List<Long> groupIds) {
        return contestProcessGroupRepository.findAllByProcessAndIdInForUpdate(process, groupIds)
                .stream()
                .map(ContestProcessGroup::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroup> findAllByProcessAndGroupNotInForUpdate(ContestProcess process, List<ContestGroup> groups) {
        return contestProcessGroupRepository.findAllByProcessAndGroupNotInForUpdate(process, groups)
                .stream()
                .map(ContestProcessGroup::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGroupsInProcessByProcessAndGroupIdIn(ContestProcess process, List<Long> groupIds) {
        contestProcessGroupRepository.deleteAllByProcessAndGroupIdIn(process, groupIds);
    }
}
