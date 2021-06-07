package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.*;
import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestGroupDTO;
import com.bjfu.contest.pojo.entity.*;
import com.bjfu.contest.pojo.request.group.GroupCreateRequest;
import com.bjfu.contest.pojo.request.group.GroupEditRequest;
import com.bjfu.contest.pojo.request.group.GroupJoinRequest;
import com.bjfu.contest.pojo.request.group.GroupKickMemberRequest;
import com.bjfu.contest.service.ContestGroupService;
import com.bjfu.contest.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContestGroupServiceImpl implements ContestGroupService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private ContestProcessDAO contestProcessDAO;
    @Autowired
    private ContestRegisterDAO contestRegisterDAO;
    @Autowired
    private ContestGroupDAO contestGroupDAO;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourceDAO resourceDAO;

    @Override
    public ContestGroupDTO getInfo(Long groupId) {
        ContestGroup group = contestGroupDAO.findById(groupId)
                .orElseThrow(() -> new BizException(ResultEnum.GROUP_NOT_EXIST));
        return new ContestGroupDTO(group, true, true, true,
                true, true, true);
    }

    @Override
    public List<ContestGroupDTO> listAllByContest(Long contestId) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        return contestGroupDAO.findAllByContest(contest)
                .stream()
                .map(group -> new ContestGroupDTO(group, false, true, true,
                        false, false, false))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroupDTO> listAllByProcess(Long processId) {
        ContestProcess process = contestProcessDAO.findById(processId)
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
        return contestGroupDAO.findAllByProcess(process)
                .stream()
                .map(group -> new ContestGroupDTO(group, false, true, true,
                        false, false, false))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestGroupDTO> listAllByMember(String account) {
        User member = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        return contestGroupDAO.findAllByMember(member)
                .stream()
                .map(group -> new ContestGroupDTO(group, true, true, true,
                        false, false, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContestGroupDTO create(GroupCreateRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 查询竞赛是否为注册状态
        if(!contest.getStatus().equals(ContestStatusEnum.REGISTERING)) {
            throw new BizException(ResultEnum.CONTEST_NOT_REGISTERING);
        }
        // 查询竞赛首个流程是否为运行状态
        ContestProcess firstProcess = contest.getProcesses()
                .stream()
                .findFirst().orElse(null);
        Boolean isOnlyExistRegisterProcess = Optional.ofNullable(firstProcess)
                .map(process -> process.getStatus().equals(ContestProcessStatusEnum.RUNNING))
                .orElseThrow(() -> new BizException(ResultEnum.REGISTER_PROCESS_NOT_EXIST));
        if(!isOnlyExistRegisterProcess) {
            throw new BizException(ResultEnum.REGISTER_PROCESS_NOT_RUNNING);
        }
        User captain = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        // 判断是否报名竞赛
        ContestRegister captainRegister = contestRegisterDAO.findByContestAndUserForUpdate(contest, captain)
                .orElseThrow(() -> new BizException(ResultEnum.USER_NOT_REGISTERED));
        // 判断是否参加过同一个竞赛里的其他队伍
        if(!contestGroupDAO.findAllByContestAndMemberForUpdate(contest, captainRegister).isEmpty()) {
            throw new BizException(ResultEnum.HAS_JOINED_GROUP);
        }
        ContestGroup group = new ContestGroup();
        BeanUtils.copyProperties(request, group);
        group.setContest(contest);
        group.setCaptain(captain);
        contestGroupDAO.insert(group);
        contestGroupDAO.addMember(contest, group, captainRegister);
        contestGroupDAO.addToProcess(group, firstProcess);
        return new ContestGroupDTO(group, false, false, false,
                false, false, false);
    }

    @Override
    @Transactional
    public void edit(GroupEditRequest request, String account) {
        // 加锁获取队伍
        ContestGroup group = contestGroupDAO.findByIdForUpdate(request.getGroupId())
                .orElseThrow(() -> new BizException(ResultEnum.GROUP_NOT_EXIST));
        Contest contest = group.getContest();
        // 验证是队长或竞赛创建人
        if(!group.getCaptain().getAccount().equals(account) && !contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_GROUP_CAPTAIN);
        }
        // 队长需要的校验
        if(group.getCaptain().getAccount().equals(account)) {
            // 验证竞赛注册状态
            if(!contest.getStatus().equals(ContestStatusEnum.REGISTERING)) {
                throw new BizException(ResultEnum.CONTEST_NOT_REGISTERING);
            }
            // 验证竞赛首个流程是运行状态
            ContestProcess firstProcess = contest.getProcesses()
                    .stream()
                    .findFirst().orElse(null);
            Boolean isOnlyExistRegisterProcess = Optional.ofNullable(firstProcess)
                    .map(process -> process.getStatus().equals(ContestProcessStatusEnum.RUNNING))
                    .orElseThrow(() -> new BizException(ResultEnum.REGISTER_PROCESS_NOT_EXIST));
            if(!isOnlyExistRegisterProcess) {
                throw new BizException(ResultEnum.REGISTER_PROCESS_NOT_RUNNING);
            }
        }
        BeanUtils.copyProperties(request, group);
        contestGroupDAO.update(group);
    }

    @Override
    @Transactional
    public void delete(Long groupId, String account) {
        // 加锁获取队伍
        ContestGroup group = contestGroupDAO.findByIdForUpdate(groupId)
                .orElseThrow(() -> new BizException(ResultEnum.GROUP_NOT_EXIST));
        Contest contest = group.getContest();
        // 验证竞赛是注册状态
        if(!contest.getStatus().equals(ContestStatusEnum.REGISTERING)) {
            throw new BizException(ResultEnum.CONTEST_NOT_REGISTERING);
        }
        // 验证是队长或竞赛创建人
        if(!group.getCaptain().getAccount().equals(account) && !contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_GROUP_CAPTAIN);
        }
        // 查询竞赛首个流程是否为运行状态
        ContestProcess firstProcess = contest.getProcesses()
                .stream()
                .findFirst().orElse(null);
        Boolean isOnlyExistRegisterProcess = Optional.ofNullable(firstProcess)
                .map(process -> process.getStatus().equals(ContestProcessStatusEnum.RUNNING))
                .orElseThrow(() -> new BizException(ResultEnum.REGISTER_PROCESS_NOT_EXIST));
        if(!isOnlyExistRegisterProcess) {
            throw new BizException(ResultEnum.REGISTER_PROCESS_NOT_RUNNING);
        }
        // 删除队伍
        contestGroupDAO.delete(group);
    }

    @Override
    @Transactional
    public void join(GroupJoinRequest request, String account) {
        // 加锁获取队伍
        ContestGroup group = contestGroupDAO.findByIdForUpdate(request.getGroupId())
                .orElseThrow(() -> new BizException(ResultEnum.GROUP_NOT_EXIST));
        Contest contest = group.getContest();
        // 确认竞赛为注册状态
        if(!contest.getStatus().equals(ContestStatusEnum.REGISTERING)) {
            throw new BizException(ResultEnum.CONTEST_NOT_REGISTERING);
        }
        // 查询竞赛首个流程是否为运行状态
        ContestProcess firstProcess = group.getContest().getProcesses()
                .stream()
                .findFirst().orElse(null);
        Boolean isOnlyExistRegisterProcess = Optional.ofNullable(firstProcess)
                .map(process -> process.getStatus().equals(ContestProcessStatusEnum.RUNNING))
                .orElseThrow(() -> new BizException(ResultEnum.REGISTER_PROCESS_NOT_EXIST));
        if(!isOnlyExistRegisterProcess) {
            throw new BizException(ResultEnum.REGISTER_PROCESS_NOT_RUNNING);
        }
        User user = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        // 判断是否报名竞赛
        ContestRegister userRegister = contestRegisterDAO.findByContestAndUserForUpdate(contest, user)
                .orElseThrow(() -> new BizException(ResultEnum.USER_NOT_REGISTERED));
        // 判断是否参加过同一个竞赛里的其他队伍
        if(!contestGroupDAO.findAllByContestAndMemberForUpdate(contest, userRegister).isEmpty()) {
            throw new BizException(ResultEnum.HAS_JOINED_GROUP);
        }
        contestGroupDAO.addMember(contest, group, userRegister);
    }

    @Override
    @Transactional
    public void kickMember(GroupKickMemberRequest request, String account) {
        // 加锁获取队伍
        ContestGroup group = contestGroupDAO.findByIdForUpdate(request.getGroupId())
                .orElseThrow(() -> new BizException(ResultEnum.GROUP_NOT_EXIST));
        Contest contest = group.getContest();
        // 确认竞赛为注册状态
        if(!contest.getStatus().equals(ContestStatusEnum.REGISTERING)) {
            throw new BizException(ResultEnum.CONTEST_NOT_REGISTERING);
        }
        // 验证是队长或竞赛创建人或踢出自己
        if(!group.getCaptain().getAccount().equals(account)
                && !contest.getCreator().getAccount().equals(account)
                && !account.equals(request.getUserAccount())) {
            throw new BizException(ResultEnum.NOT_GROUP_CAPTAIN);
        }
        // 查询竞赛首个流程是否为运行状态
        ContestProcess firstProcess = group.getContest().getProcesses()
                .stream()
                .findFirst().orElse(null);
        Boolean isOnlyExistRegisterProcess = Optional.ofNullable(firstProcess)
                .map(process -> process.getStatus().equals(ContestProcessStatusEnum.RUNNING))
                .orElseThrow(() -> new BizException(ResultEnum.REGISTER_PROCESS_NOT_EXIST));
        if(!isOnlyExistRegisterProcess) {
            throw new BizException(ResultEnum.REGISTER_PROCESS_NOT_RUNNING);
        }
        // 不能踢出队长
        if(group.getCaptain().getAccount().equals(request.getUserAccount())) {
            throw new BizException(ResultEnum.GROUP_CAPTAIN_CAN_NOT_KICK);
        }
        User user = userDAO.findByAccount(request.getUserAccount())
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        // 判断是否报名竞赛
        ContestRegister userRegister = contestRegisterDAO.findByContestAndUserForUpdate(contest, user)
                .orElseThrow(() -> new BizException(ResultEnum.USER_NOT_REGISTERED));
        // 删除关系
        contestGroupDAO.deleteMember(contest, group, userRegister);
    }
}
