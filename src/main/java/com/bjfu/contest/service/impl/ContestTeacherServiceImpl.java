package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.ContestTeacherDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestTeacher;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.teacher.TeacherCreateRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherDeleteRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherListAllTeachContestsRequest;
import com.bjfu.contest.service.ContestTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContestTeacherServiceImpl implements ContestTeacherService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private ContestTeacherDAO contestTeacherDAO;

    @Override
    @Transactional
    public List<UserDTO> create(TeacherCreateRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        List<User> teachers = userDAO.findAllByAccountIn(request.getTeacherAccounts())
                .stream()
                .filter(user -> user.getType().equals(UserTypeEnum.TEACHER))
                .collect(Collectors.toList());

        if(contestTeacherDAO.findByContestAndTeacherInForUpdate(contest, teachers).isPresent()) {
            throw new BizException(ResultEnum.TEACHER_REGISTERED);
        }
        List<ContestTeacher> contestTeachers = teachers.stream().map(teacher -> {
            ContestTeacher contestTeacher = new ContestTeacher();
            contestTeacher.setContest(contest);
            contestTeacher.setTeacher(teacher);
            return contestTeacher;
        }).collect(Collectors.toList());
        contestTeachers = contestTeacherDAO.insertAll(contestTeachers);
        return contestTeachers.stream()
                .map(contestTeacher -> new UserDTO(contestTeacher.getTeacher()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(TeacherDeleteRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        User teacher = userDAO.findActiveUserByAccount(request.getTeacherAccount())
                .filter(user -> user.getType().equals(UserTypeEnum.TEACHER))
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        ContestTeacher contestTeacher = contestTeacherDAO.findByContestAndTeacherForUpdate(contest, teacher)
                .orElseThrow(() -> new BizException(ResultEnum.TEACHER_NOT_REGISTERED));
        if(!contest.getCreator().getAccount().equals(account) && !contestTeacher.getTeacher().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        contestTeacherDAO.delete(contestTeacher);
    }

    @Override
    public List<UserDTO> listAllByContest(Long contestId) {
        Contest contest = contestDAO.findById(contestId)
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        List<ContestTeacher> teachers = contestTeacherDAO.findAllByContest(contest);
        return teachers.stream()
                .map(contestTeacher -> new UserDTO(contestTeacher.getTeacher()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ContestDTO> listAllByAccount(TeacherListAllTeachContestsRequest request, String account) {
        String contestName = Optional.ofNullable(request)
                .map(TeacherListAllTeachContestsRequest::getContestName)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        String creatorName = Optional.ofNullable(request)
                .map(TeacherListAllTeachContestsRequest::getCreatorName)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        String creatorCollege = Optional.ofNullable(request)
                .map(TeacherListAllTeachContestsRequest::getCreatorCollege)
                .flatMap(list -> list.stream().findFirst())
                .orElse(null);
        User teacher = userDAO.findActiveUserByAccount(account)
                .filter(user -> user.getType().equals(UserTypeEnum.TEACHER))
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        Page<ContestTeacher> contests = contestTeacherDAO.findAllByTeacherAndContestNameLikeAndContestStatusInAndCreatorNameLikeAndCreatorCollegeLike(teacher, contestName, request.getContestStatus(), creatorName, creatorCollege, request.getPagination(), request.getSorter());
        return contests.map(contestTeacher -> new ContestDTO(contestTeacher.getContest(), true, false, false, false, false, false));
    }
}
