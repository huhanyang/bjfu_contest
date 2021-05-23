package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.ContestProcessDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestProcess;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.contest.*;
import com.bjfu.contest.service.ContestService;
import com.bjfu.contest.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContestProcessDAO contestProcessDAO;
    @Autowired
    private ResourceService resourceService;

    @Override
    @Transactional
    public ContestDTO create(ContestCreateRequest request, String account) {
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        Contest contest = new Contest();
        BeanUtils.copyProperties(request, contest);
        contest.setCreator(user);
        contestDAO.insert(contest);
        // 创建默认报名组队流程
        if(request.getIsCreateDefaultProcess()) {
            ContestProcess defaultProcess = new ContestProcess();
            defaultProcess.setContest(contest);
            defaultProcess.setName("报名组队流程");
            defaultProcess.setSort(1);
            defaultProcess.setDescription("报名组队流程，请在此流程内报名组队，流程结束时由竞赛创建者筛选晋级的参赛队伍。");
            defaultProcess.setSubmitList("{}");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_WEEK, Calendar.DATE);
            defaultProcess.setEndSubmitTime(calendar.getTime());
            contestProcessDAO.insert(defaultProcess);
        }
        return new ContestDTO(contest, false, false, false,
                false, false, false);
    }

    @Override
    @Transactional
    public void edit(ContestEditRequest request, String account) {
        Contest contest = contestDAO.findByIdForUpdate(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 创建人校验
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        BeanUtils.copyProperties(request, contest);
        contestDAO.update(contest);
    }

    @Override
    @Transactional
    public void delete(Long contestId, String account) {
        Contest contest = contestDAO.findByIdForUpdate(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 创建人校验
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        contestDAO.delete(contest);
    }

    @Override
    public ContestDTO getInfo(Long contestId) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        return new ContestDTO(contest, true, true, true,
                false, true, true);
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
        return contests.map(contest -> new ContestDTO(contest, false, false, false,
                false, false, false));
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
        return contests.map(contest -> new ContestDTO(contest, true, false, false,
                false, false, false));
    }

    @Override
    public ResourceDTO addResource(ContestAddResourceRequest request, String account) throws IOException {
        User user = userDAO.findActiveUserByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 验证竞赛指导教师或创建者
        boolean isContestTeacher = contest.getTeachers()
                .stream()
                .anyMatch(contestTeacher -> contestTeacher.getTeacher().getAccount().equals(account));
        if(!contest.getCreator().getAccount().equals(account) && !isContestTeacher) {
            throw new BizException(ResultEnum.TEACHER_NOT_REGISTERED);
        }
        return resourceService.create(user, request.getContestId(),
                request.getFileName(), ResourceTypeEnum.CONTEST,
                ResourceContentTypeEnum.OTHER, request.getClassification(),
                request.getFile().getInputStream());
    }
}
