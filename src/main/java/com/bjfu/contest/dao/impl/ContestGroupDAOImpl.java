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
    public ContestGroupMember addMember(Contest contest, ContestGroup group, User member) {
        ContestGroupMember groupMember = new ContestGroupMember();
        groupMember.setContest(contest);
        groupMember.setGroup(group);
        groupMember.setMember(member);
        contestGroupMemberRepository.save(groupMember);
        return groupMember;
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
    public List<ContestGroup> findAllByContestAndMember(Contest contest, User member) {
        return contestGroupMemberRepository.findAllByContestAndMember(contest, member)
                .stream()
                .map(ContestGroupMember::getGroup)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroup> findAllByContestAndMemberForUpdate(Contest contest, User member) {
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
}
