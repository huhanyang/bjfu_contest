package com.bjfu.contest.controller;

import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BaseAppException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.ContestGroupDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.group.GroupCreateRequest;
import com.bjfu.contest.pojo.request.group.GroupEditRequest;
import com.bjfu.contest.pojo.request.group.GroupJoinRequest;
import com.bjfu.contest.pojo.request.group.GroupKickMemberRequest;
import com.bjfu.contest.pojo.vo.ContestGroupVO;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.security.annotation.RequireStudent;
import com.bjfu.contest.service.ContestGroupService;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/contest/group")
public class ContestGroupController {

    @Autowired
    private ContestGroupService contestGroupService;

    @RequireLogin
    @GetMapping("/getInfo")
    public BaseResult<ContestGroupVO> getInfo(@NotNull(message = "队伍id不能为空") Long groupId) {
        ContestGroupDTO group = contestGroupService.getInfo(groupId);
        return BaseResult.success(new ContestGroupVO(group));
    }

    @RequireLogin
    @GetMapping("/listAllByContest")
    public BaseResult<List<ContestGroupVO>> listAllByContest(@NotNull(message = "竞赛id不能为空") Long contestId) {
        List<ContestGroupVO> groups = contestGroupService.listAllByContest(contestId)
                .stream()
                .map(ContestGroupVO::new)
                .collect(Collectors.toList());
        return BaseResult.success(groups);
    }

    @RequireLogin
    @GetMapping("/listAllByProcess")
    public BaseResult<List<ContestGroupVO>> listAllByProcess(@NotNull(message = "流程id不能为空") Long processId) {
        List<ContestGroupVO> groups = contestGroupService.listAllByProcess(processId)
                .stream()
                .map(ContestGroupVO::new)
                .collect(Collectors.toList());
        return BaseResult.success(groups);
    }

    @RequireLogin
    @GetMapping("/listAllByMember")
    public BaseResult<List<ContestGroupVO>> listAllByMember() {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        List<ContestGroupVO> groups = contestGroupService.listAllByMember(userDTO.getAccount())
                .stream()
                .map(ContestGroupVO::new)
                .collect(Collectors.toList());
        return BaseResult.success(groups);
    }

    @RequireStudent
    @PostMapping("/create")
    public BaseResult<ContestGroupVO> create(@Validated @RequestBody GroupCreateRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        ContestGroupDTO groupDTO = contestGroupService.create(request, userDTO.getAccount());
        return BaseResult.success(new ContestGroupVO(groupDTO));
    }

    @RequireLogin
    @PostMapping("/edit")
    public BaseResult<Void> edit(@Validated @RequestBody GroupEditRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestGroupService.edit(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @DeleteMapping("/delete")
    public BaseResult<Void> delete(@NotNull(message = "队伍id不为空") Long groupId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestGroupService.delete(groupId, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireStudent
    @PostMapping("/join")
    public BaseResult<Void> join(@Validated @RequestBody GroupJoinRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestGroupService.join(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @PostMapping("/kickMember")
    public BaseResult<Void> kickMember(@Validated @RequestBody GroupKickMemberRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new BaseAppException(ResultEnum.USER_CONTEXT_ERROR));
        contestGroupService.kickMember(request, userDTO.getAccount());
        return BaseResult.success();
    }

}
