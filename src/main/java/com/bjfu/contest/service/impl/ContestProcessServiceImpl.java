package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.ContestProcessDAO;
import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestProcessDTO;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestProcess;
import com.bjfu.contest.pojo.request.process.ProcessCreateRequest;
import com.bjfu.contest.pojo.request.process.ProcessEditRequest;
import com.bjfu.contest.service.ContestProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContestProcessServiceImpl implements ContestProcessService {

    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private ContestProcessDAO contestProcessDAO;

    @Override
    public ContestProcessDTO getInfo(Long contestId, Long processId) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        return contestProcessDAO.findById(processId)
                .map(ContestProcessDTO::new)
                .orElseThrow(() -> new BizException(ResultEnum.PROCESS_NOT_EXIST));
    }

    @Override
    public List<ContestProcessDTO> listAll(Long contestId) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        return contest.getProcesses()
                .stream()
                .sorted(Comparator.comparingInt(ContestProcess::getSort))
                .map(ContestProcessDTO::new)
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
        return new ContestProcessDTO(contestProcess);
    }

    @Override
    @Transactional
    public ContestProcessDTO edit(ProcessEditRequest request, String account) {
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
        return new ContestProcessDTO(process);
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
}
