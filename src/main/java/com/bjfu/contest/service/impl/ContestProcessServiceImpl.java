package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.ContestGroupDAO;
import com.bjfu.contest.dao.ContestProcessDAO;
import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestGroupDTO;
import com.bjfu.contest.pojo.dto.ContestProcessDTO;
import com.bjfu.contest.pojo.entity.*;
import com.bjfu.contest.pojo.request.process.ProcessCreateRequest;
import com.bjfu.contest.pojo.request.process.ProcessDemoteGroupsRequest;
import com.bjfu.contest.pojo.request.process.ProcessEditRequest;
import com.bjfu.contest.pojo.request.process.ProcessPromoteGroupsRequest;
import com.bjfu.contest.service.ContestProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContestProcessServiceImpl implements ContestProcessService {

    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private ContestProcessDAO contestProcessDAO;
    @Autowired
    private ContestGroupDAO contestGroupDAO;

    @Override
    public ContestProcessDTO getInfo(Long processId) {
        return contestProcessDAO.findById(processId)
                .map(process -> new ContestProcessDTO(process, true, true))
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
    }

    @Override
    public List<ContestProcessDTO> listAll(Long contestId) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        return contest.getProcesses()
                .stream()
                .sorted(Comparator.comparingInt(ContestProcess::getSort))
                .map(process -> new ContestProcessDTO(process, false, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContestProcessDTO create(ProcessCreateRequest request, String account) {
        Contest contest = contestDAO.findByIdForUpdate(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 验证竞赛创建者
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        Optional<ContestProcess> lastProcess = contest.getProcesses()
                .stream()
                .max(Comparator.comparingInt(ContestProcess::getSort));
        ContestProcessStatusEnum lastProcessStatus = lastProcess.map(ContestProcess::getStatus)
                .orElse(ContestProcessStatusEnum.FINISH);
        // 验证之前的流程全部结束
        if(!lastProcessStatus.equals(ContestProcessStatusEnum.FINISH)) {
            throw new BizException(ResultEnum.EXIST_PROCESS_NOT_FINISH);
        }
        Integer lastProcessSort = lastProcess.map(ContestProcess::getSort).orElse(0);
        ContestProcess contestProcess = new ContestProcess();
        BeanUtils.copyProperties(request, contestProcess);
        contestProcess.setContest(contest);
        contestProcess.setSort(lastProcessSort + 1);
        contestProcessDAO.insert(contestProcess);
        return new ContestProcessDTO(contestProcess, false, false);
    }

    @Override
    @Transactional
    public void edit(ProcessEditRequest request, String account) {
        Contest contest = contestDAO.findByIdForUpdate(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 验证竞赛创建者
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        ContestProcess process = contestProcessDAO.findById(request.getProcessId())
                .filter(contestProcess -> contestProcess.getContest().getId().equals(contest.getId()))
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
        // 验证流程未完成
        if(process.getStatus().equals(ContestProcessStatusEnum.FINISH)) {
            throw new BizException(ResultEnum.PROCESS_FINISHED);
        }
        BeanUtils.copyProperties(request, process);
        contestProcessDAO.update(process);
    }

    @Override
    @Transactional
    public void delete(Long contestId, Long processId, String account) {
        Contest contest = contestDAO.findByIdForUpdate(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 验证竞赛创建者
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        ContestProcess process = contestProcessDAO.findById(processId)
                .filter(contestProcess -> contestProcess.getContest().getId().equals(contest.getId()))
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
        // 验证流程是否为创建状态
        if(!process.getStatus().equals(ContestProcessStatusEnum.CREATING)) {
            throw new BizException(ResultEnum.PROCESS_NOT_CREATING);
        }
        contestProcessDAO.delete(process);
    }

    @Override
    public List<ContestGroupDTO> listPromotableGroups(Long targetProcessId, String account) {
        ContestProcess process = contestProcessDAO.findById(targetProcessId)
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
        // 有上一个流程则从上一个中选
        if(process.getSort() != 1) {
            // 确认存在上一个sort连续的流程
            ContestProcess beforeProcess = contestProcessDAO.findByContestAndSort(process.getContest(), process.getSort() - 1)
                    .orElseThrow(() -> new BizException(ResultEnum.PROCESS_SORT_ERROR));
            // 验证上一个流程为结束状态
            if(!beforeProcess.getStatus().equals(ContestProcessStatusEnum.FINISH)) {
                throw new BizException(ResultEnum.PROCESS_BEFORE_STATUS_ERROR);
            }
            // 查找上一个流程里的所有队伍
            return contestGroupDAO.findAllByProcess(beforeProcess)
                    .stream()
                    .map(group -> new ContestGroupDTO(group, false, true, true,
                            false, false, false))
                    .collect(Collectors.toList());
        }
        // 首个流程则从竞赛所有队伍中选
        return contestGroupDAO.findAllByContest(process.getContest())
                .stream()
                .map(group -> new ContestGroupDTO(group, false, true, true,
                        false, false, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void promoteGroups(ProcessPromoteGroupsRequest request, String account) {
        ContestProcess process = contestProcessDAO.findById(request.getProcessId())
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
        // 竞赛创建人验证
        if(!process.getContest().getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        // 验证流程是创建状态
        if(!process.getStatus().equals(ContestProcessStatusEnum.CREATING)) {
            throw new BizException(ResultEnum.PROCESS_NOT_CREATING);
        }
        if(process.getSort() != 1) {
            // 确认存在上一个sort连续的流程
            ContestProcess beforeProcess = contestProcessDAO.findByContestAndSort(process.getContest(), process.getSort() - 1)
                    .orElseThrow(() -> new BizException(ResultEnum.PROCESS_SORT_ERROR));
            // 验证上一个流程为结束状态
            if(!beforeProcess.getStatus().equals(ContestProcessStatusEnum.FINISH)) {
                throw new BizException(ResultEnum.PROCESS_BEFORE_STATUS_ERROR);
            }
            // 从上一个流程队伍中过滤出存在的队伍
            List<ContestGroup> groups = contestGroupDAO.findAllByProcessAndIdInForUpdate(beforeProcess, request.getGroupIds());
            Set<Long> existGroupIds = contestGroupDAO.findAllByProcess(process)
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toSet());
            // 过滤出目标流程中不存在的队伍
            groups = groups.stream().filter(group -> !existGroupIds.contains(group.getId())).collect(Collectors.toList());
            // 保存这些队伍
            contestGroupDAO.addAllToProcess(process, groups);
        } else {
            // 找到竞赛全部队伍
            List<ContestGroup> groups = contestGroupDAO.findAllByContest(process.getContest());
            // 过滤出目标流程中不存在的队伍
            Set<Long> existGroupIds = contestGroupDAO.findAllByProcess(process)
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toSet());
            // 过滤出目标流程中不存在的队伍
            groups = groups.stream().filter(group -> !existGroupIds.contains(group.getId())).collect(Collectors.toList());
            // 保存这些队伍
            contestGroupDAO.addAllToProcess(process, groups);
        }
        // todo 更新上一流程中的状态
    }

    @Override
    @Transactional
    public void demoteGroups(ProcessDemoteGroupsRequest request, String account) {
        ContestProcess process = contestProcessDAO.findById(request.getProcessId())
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
        // 竞赛创建人验证
        if(!process.getContest().getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        // 验证流程是创建状态
        if(!process.getStatus().equals(ContestProcessStatusEnum.CREATING)) {
            throw new BizException(ResultEnum.PROCESS_NOT_CREATING);
        }
        // 删除流程队伍关联关系
        contestGroupDAO.deleteGroupsInProcessByProcessAndGroupIdIn(process, request.getGroupIds());
        // todo 更新上一流程中的状态
    }
}
