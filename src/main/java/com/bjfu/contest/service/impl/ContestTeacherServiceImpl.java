package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.dao.ContestGroupDAO;
import com.bjfu.contest.dao.ContestTeacherDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.enums.UserTypeEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.entity.*;
import com.bjfu.contest.pojo.request.teacher.TeacherCreateRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherDeleteRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherJoinGroupRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherListAllTeachContestsRequest;
import com.bjfu.contest.service.ContestTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContestTeacherServiceImpl implements ContestTeacherService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContestDAO contestDAO;
    @Autowired
    private ContestTeacherDAO contestTeacherDAO;
    @Autowired
    private ContestGroupDAO contestGroupDAO;

    @Override
    @Transactional
    public List<UserDTO> create(TeacherCreateRequest request, String account) {
        Contest contest = contestDAO.findById(request.getContestId())
                .orElseThrow(() -> new BizException(ResultEnum.CONTEST_NOT_EXIST));
        // 验证竞赛创建者
        if(!contest.getCreator().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_CONTEST_CREATOR);
        }
        // 取出所有是教师的用户
        List<User> teachers = userDAO.findAllByAccountIn(request.getTeacherAccounts())
                .stream()
                .filter(user -> user.getType().equals(UserTypeEnum.TEACHER))
                .collect(Collectors.toList());
        // 找出存在的指导教师
        Set<Long> registeredTeacherIds = contestTeacherDAO.findByContestAndTeachersInForUpdate(contest, teachers)
                .stream()
                .map(contestTeacher -> contestTeacher.getTeacher().getId())
                .collect(Collectors.toSet());
        // 过滤掉竞赛中存在的教师
        teachers = teachers.stream()
                .filter(teacher -> !registeredTeacherIds.contains(teacher.getId()))
                .collect(Collectors.toList());
        // 构造出所有的教师竞赛关系
        List<ContestTeacher> contestTeachers = teachers.stream().map(teacher -> {
            ContestTeacher contestTeacher = new ContestTeacher();
            contestTeacher.setContest(contest);
            contestTeacher.setTeacher(teacher);
            return contestTeacher;
        }).collect(Collectors.toList());
        //保存竞赛教师关系
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
    @Transactional
    public void joinGroup(TeacherJoinGroupRequest request, String account) {
        // 加锁获取队伍
        ContestGroup group = contestGroupDAO.findByIdForUpdate(request.getGroupId())
                .orElseThrow(() -> new BizException(ResultEnum.GROUP_NOT_EXIST));
        // 验证不存在指导教师
        if(group.getTeacher() != null) {
            throw new BizException(ResultEnum.GROUP_TEACHER_EXIST);
        }
        Contest contest = group.getContest();
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
        // 验证教师是竞赛指导教师
        User teacher = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        contestTeacherDAO.findByContestAndTeacherForUpdate(contest, teacher)
                .orElseThrow(() -> new BizException(ResultEnum.TEACHER_NOT_REGISTERED));
        group.setTeacher(teacher);
        contestGroupDAO.update(group);
    }

    @Override
    @Transactional
    public void quitGroup(Long groupId, String account) {
        // 加锁获取队伍
        ContestGroup group = contestGroupDAO.findByIdForUpdate(groupId)
                .orElseThrow(() -> new BizException(ResultEnum.GROUP_NOT_EXIST));
        // 验证存在指导教师
        if(group.getTeacher() == null || !group.getTeacher().getAccount().equals(account)) {
            throw new BizException(ResultEnum.NOT_GROUP_TEACHER);
        }
        Contest contest = group.getContest();
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
        // 删除指导教师
        group.setTeacher(null);
        contestGroupDAO.update(group);
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
