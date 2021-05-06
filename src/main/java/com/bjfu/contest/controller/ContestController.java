package com.bjfu.contest.controller;

import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.AppException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.contest.ContestCreateRequest;
import com.bjfu.contest.pojo.request.contest.ContestEditRequest;
import com.bjfu.contest.pojo.vo.ContestVO;
import com.bjfu.contest.security.annotation.RequireTeacher;
import com.bjfu.contest.service.ContestService;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @RequireTeacher
    @PostMapping("/create")
    public BaseResult<ContestVO> create(@Validated @RequestBody ContestCreateRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        ContestDTO contestDTO = contestService.create(request, userDTO.getAccount());
        return BaseResult.success(new ContestVO(contestDTO));
    }

    @RequireTeacher
    @PostMapping("/edit")
    public BaseResult<ContestVO> edit(@Validated @RequestBody ContestEditRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        ContestDTO contestDTO = contestService.edit(request, userDTO.getAccount());
        return BaseResult.success(new ContestVO(contestDTO));
    }

    @RequireTeacher
    @DeleteMapping("/delete")
    public BaseResult<Void> delete(@NotNull(message = "竞赛id不能为空!") Long contestId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestService.delete(contestId, userDTO.getAccount());
        return BaseResult.success();
    }

    @GetMapping("/getInfo")
    public BaseResult<ContestVO> getInfo(@NotNull(message = "竞赛id不能为空!") Long contestId) {
        ContestDTO contestDTO = contestService.getInfo(contestId);
        return BaseResult.success(new ContestVO(contestDTO));
    }

}
