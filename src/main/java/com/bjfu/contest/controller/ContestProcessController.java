package com.bjfu.contest.controller;

import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BaseAppException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.ContestProcessDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.process.ProcessCreateRequest;
import com.bjfu.contest.pojo.request.process.ProcessDemoteGroupsRequest;
import com.bjfu.contest.pojo.request.process.ProcessEditRequest;
import com.bjfu.contest.pojo.request.process.ProcessPromoteGroupsRequest;
import com.bjfu.contest.pojo.vo.ContestGroupVO;
import com.bjfu.contest.pojo.vo.ContestProcessVO;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.security.annotation.RequireTeacher;
import com.bjfu.contest.service.ContestProcessService;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/contest/process")
public class ContestProcessController {

    @Autowired
    private ContestProcessService contestProcessService;

    @RequireLogin
    @GetMapping("/getInfo")
    public BaseResult<ContestProcessVO> getInfo(@NotNull(message = "流程id不能为空!") Long processId) {
        ContestProcessDTO processDTO = contestProcessService.getInfo(processId);
        return BaseResult.success(new ContestProcessVO(processDTO));
    }

    @RequireLogin
    @GetMapping("/listAll")
    public BaseResult<List<ContestProcessVO>> listAll(@NotNull(message = "竞赛id不能为空!") Long contestId) {
        List<ContestProcessVO> processVOList = contestProcessService.listAll(contestId)
                .stream()
                .map(ContestProcessVO::new)
                .collect(Collectors.toList());
        return BaseResult.success(processVOList);
    }

    @RequireTeacher
    @PostMapping("/create")
    public BaseResult<ContestProcessVO> create(@Validated @RequestBody ProcessCreateRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        ContestProcessDTO processDTO = contestProcessService.create(request, userDTO.getAccount());
        return BaseResult.success(new ContestProcessVO(processDTO));
    }

    @RequireTeacher
    @PostMapping("/edit")
    public BaseResult<Void> edit(@Validated @RequestBody ProcessEditRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestProcessService.edit(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireTeacher
    @DeleteMapping("/delete")
    public BaseResult<Void> delete(@NotNull(message = "竞赛id不能为空!") Long contestId,
                                   @NotNull(message = "流程id不能为空!") Long processId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestProcessService.delete(contestId, processId, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @GetMapping("/listPromotableGroups")
    public BaseResult<List<ContestGroupVO>> listPromotableGroups(@NotNull(message = "目标流程id不能为空!") Long targetProcessId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        List<ContestGroupVO> groups = contestProcessService.listPromotableGroups(targetProcessId, userDTO.getAccount())
                .stream()
                .map(ContestGroupVO::new)
                .collect(Collectors.toList());
        return BaseResult.success(groups);
    }

    @RequireLogin
    @PostMapping("/promoteGroups")
    public BaseResult<Void> promoteGroups(@Validated @RequestBody ProcessPromoteGroupsRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestProcessService.promoteGroups(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @PostMapping("/demoteGroups")
    public BaseResult<Void> demoteGroups(@Validated @RequestBody ProcessDemoteGroupsRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestProcessService.demoteGroups(request, userDTO.getAccount());
        return BaseResult.success();
    }

}
