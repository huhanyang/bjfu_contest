package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.ContestRegisterDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestRegisterDTO;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.contest.ContestListAllRequest;
import com.bjfu.contest.pojo.request.register.RegisterBanRequest;
import com.bjfu.contest.pojo.request.register.RegisterCreateRequest;
import com.bjfu.contest.pojo.request.register.RegisterListAllRequest;
import com.bjfu.contest.service.ContestRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContestRegisterServiceImpl implements ContestRegisterService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private ContestRegisterDAO contestRegisterDAO;

    @Override
    public Page<ContestRegisterDTO> listAll(RegisterListAllRequest request) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        String registerName = Optional.of(request)
                .map(RegisterListAllRequest::getRegisterName)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        String registerGrade = Optional.of(request)
                .map(RegisterListAllRequest::getRegisterGrade)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        String registerCollege = Optional.of(request)
                .map(RegisterListAllRequest::getRegisterCollege)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        String registerMajor = Optional.of(request)
                .map(RegisterListAllRequest::getRegisterMajor)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        return contestRegisterDAO.findAllByContestAndStatusInAndRegisterLike(contest,
                request.getStatus(),
                registerName,
                registerGrade,
                registerCollege,
                registerMajor,
                request.getPagination(),
                request.getSorter())
                .map(contestRegister -> new ContestRegisterDTO(contestRegister, false, true, false));
    }

    @Override
    public List<ContestRegisterDTO> listAllRegistered(String account) {
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        return contestRegisterDAO.findAllByUser(user)
                .stream()
                .map(contestRegister -> new ContestRegisterDTO(contestRegister, true, false, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(RegisterCreateRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        if(contestRegisterDAO.findByContestAndUserForUpdate(contest, user).isPresent()) {
            throw new BizException(ResultEnum.USER_REGISTERED);
        }
        ContestRegister contestRegister = new ContestRegister();
        contestRegister.setContest(contest);
        contestRegister.setUser(user);
        contestRegisterDAO.insert(contestRegister);

    }

    @Override
    @Transactional
    public void delete(Long contestId, String account) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        if(!contest.getStatus().equals(ContestStatusEnum.REGISTERING)) {
            throw new BizException(ResultEnum.CONTEST_NOT_REGISTERING);
        }
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        ContestRegister contestRegister = contestRegisterDAO.findByContestAndUserForUpdate(contest, user)
                .orElseThrow(() -> new BizException(ResultEnum.USER_NOT_REGISTERED));
        contestRegisterDAO.delete(contestRegister);
    }

    @Override
    @Transactional
    public void ban(RegisterBanRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        User user = userDAO.findById(request.getUserId())
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        ContestRegister contestRegister = contestRegisterDAO.findByContestAndUserForUpdate(contest, user)
                .orElseThrow(() -> new BizException(ResultEnum.USER_NOT_REGISTERED));
        contestRegister.setStatus(ContestRegisterStatusEnum.BAN);
        contestRegisterDAO.update(contestRegister);
    }

    @Override
    @Transactional
    public void unban(RegisterBanRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        User user = userDAO.findById(request.getUserId())
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        ContestRegister contestRegister = contestRegisterDAO.findByContestAndUserForUpdate(contest, user)
                .orElseThrow(() -> new BizException(ResultEnum.USER_NOT_REGISTERED));
        contestRegister.setStatus(ContestRegisterStatusEnum.SIGN_UP);
        contestRegisterDAO.update(contestRegister);
    }

    @Override
    public ContestRegisterDTO getInfo(Long contestId, String account) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        ContestRegister register = contestRegisterDAO.findByContestAndUser(contest, user)
                .orElseThrow(() -> new BizException(ResultEnum.USER_NOT_REGISTERED));
        return new ContestRegisterDTO(register, false, false, true);
    }
}
