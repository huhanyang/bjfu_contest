package com.bjfu.contest.controller;

import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.AppException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.ContestRegisterDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.register.RegisterBanRequest;
import com.bjfu.contest.pojo.request.register.RegisterCreateRequest;
import com.bjfu.contest.pojo.request.register.RegisterListAllRequest;
import com.bjfu.contest.pojo.vo.ContestRegisterVO;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.security.annotation.RequireStudent;
import com.bjfu.contest.security.annotation.RequireTeacher;
import com.bjfu.contest.service.ContestRegisterService;
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
@RequestMapping("/contest/register")
public class ContestRegisterController {

    @Autowired
    private ContestRegisterService contestRegisterService;

    @RequireStudent
    @PostMapping("/create")
    public BaseResult<Void> create(@Validated @RequestBody RegisterCreateRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestRegisterService.create(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireStudent
    @DeleteMapping("/delete")
    public BaseResult<Void> delete(@NotNull(message = "竞赛id不能为空!") Long contestId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestRegisterService.delete(contestId, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireTeacher
    @PostMapping("/ban")
    public BaseResult<Void> ban(@Validated @RequestBody RegisterBanRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestRegisterService.ban(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireTeacher
    @PostMapping("/unban")
    public BaseResult<Void> unban(@Validated @RequestBody RegisterBanRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        contestRegisterService.unban(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @PostMapping("/listAll")
    public BaseResult<Page<ContestRegisterVO>> listAll(@Validated @RequestBody RegisterListAllRequest request) {
        Page<ContestRegisterDTO> registerDTOS = contestRegisterService.listAll(request);
        return BaseResult.success(registerDTOS.map(ContestRegisterVO::new));
    }

    @RequireStudent
    @GetMapping("/listAllRegistered")
    public BaseResult<List<ContestRegisterVO>> listAllRegistered() {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        List<ContestRegisterDTO> registerDTOS = contestRegisterService.listAllRegistered(userDTO.getAccount());
        return BaseResult.success(registerDTOS.stream().map(ContestRegisterVO::new).collect(Collectors.toList()));
    }

    @RequireLogin
    @GetMapping("/getInfo")
    public BaseResult<ContestRegisterVO> getInfo(@NotNull(message = "竞赛id不能为空") Long contestId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        ContestRegisterDTO registerDTO = contestRegisterService.getInfo(contestId, userDTO.getAccount());
        return BaseResult.success(new ContestRegisterVO(registerDTO));
    }

}
