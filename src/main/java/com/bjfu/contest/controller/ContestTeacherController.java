package com.bjfu.contest.controller;

import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.AppException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.teacher.TeacherCreateRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherDeleteRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherListAllTeachContestsRequest;
import com.bjfu.contest.pojo.vo.ContestVO;
import com.bjfu.contest.pojo.vo.UserVO;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.security.annotation.RequireTeacher;
import com.bjfu.contest.service.ContestTeacherService;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/contest/teacher")
public class ContestTeacherController {

    @Autowired
    private ContestTeacherService contestTeacherService;

    @RequireTeacher
    @PostMapping("/create")
    public BaseResult<List<UserVO>> create(@Validated @RequestBody TeacherCreateRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        List<UserDTO> teachers = contestTeacherService.create(request, userDTO.getAccount());
        return BaseResult.success(teachers.stream().map(UserVO::new).collect(Collectors.toList()));
    }

    @RequireTeacher
    @PostMapping("/delete")
    public BaseResult<Void> delete(@Validated @RequestBody TeacherDeleteRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestTeacherService.delete(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireTeacher
    @PostMapping("/joinGroup")
    public BaseResult<Void> joinGroup(@NotNull(message = "队伍id不能为空") Long groupId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestTeacherService.joinGroup(groupId, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireTeacher
    @PostMapping("/quitGroup")
    public BaseResult<Void> quitGroup(@NotNull(message = "队伍id不能为空") Long groupId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestTeacherService.quitGroup(groupId, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @GetMapping("/listAll")
    public BaseResult<List<UserVO>> listAll(@NotNull(message = "竞赛id不能为空") Long contestId) {
        List<UserDTO> contestTeacherDTOS = contestTeacherService.listAllByContest(contestId);
        return BaseResult.success(contestTeacherDTOS.stream().map(UserVO::new).collect(Collectors.toList()));
    }

    @RequireLogin
    @PostMapping("/listAllTeachContests")
    public BaseResult<Page<ContestVO>> listAllTeachContests(@Validated @RequestBody TeacherListAllTeachContestsRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        Page<ContestDTO> contests = contestTeacherService.listAllByAccount(request, userDTO.getAccount());
        return BaseResult.success(contests.map(ContestVO::new));
    }

}
