package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.contest.ContestCreateRequest;
import com.bjfu.contest.pojo.request.contest.ContestEditRequest;
import com.bjfu.contest.pojo.request.contest.ContestListAllRequest;
import com.bjfu.contest.pojo.request.contest.ContestListCreatedRequest;
import com.bjfu.contest.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Page<ContestDTO> listCreated(ContestListCreatedRequest request, String account) {
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        String name = Optional.ofNullable(request)
                .map(ContestListCreatedRequest::getName)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        Page<Contest> contests = contestDAO.findByCreatorAndNameLikeAndStatusIn(user, name, request.getStatus(), request.getPagination(), request.getSorter());
        return contests.map(ContestDTO::new);
    }

    @Override
    public Page<ContestDTO> listAll(ContestListAllRequest request, String account) {
        String name = Optional.ofNullable(request)
                .map(ContestListAllRequest::getName)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        String creatorName = Optional.ofNullable(request)
                .map(ContestListAllRequest::getCreatorName)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        String creatorCollege = Optional.ofNullable(request)
                .map(ContestListAllRequest::getCreatorCollege)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        Page<Contest> contests = contestDAO.findByNameLikeAndStatusInAndCreatorNameLikeAndCreatorCollegeLike(name, request.getStatus(), creatorName, creatorCollege, request.getPagination(), request.getSorter());
        return contests.map(ContestDTO::new);
    }
}
