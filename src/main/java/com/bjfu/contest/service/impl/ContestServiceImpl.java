package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.enums.UserStatusEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.contest.ContestCreateRequest;
import com.bjfu.contest.pojo.request.contest.ContestEditRequest;
import com.bjfu.contest.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public ContestDTO create(ContestCreateRequest request, String account) {
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        Contest contest = new Contest();
        BeanUtils.copyProperties(request, contest);
        contest.setCreator(user);
        contestDAO.insert(contest);
        return new ContestDTO(contest);
    }

    @Override
    public ContestDTO edit(ContestEditRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        BeanUtils.copyProperties(request, contest);
        contestDAO.update(contest);
        return new ContestDTO(contest);
    }

    @Override
    public void delete(Long contestId, String account) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        contestDAO.delete(contest);
    }

    @Override
    public ContestDTO getInfo(Long contestId) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        return new ContestDTO(contest);
    }
}
