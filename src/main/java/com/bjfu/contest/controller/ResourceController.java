package com.bjfu.contest.controller;

import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.AppException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.dto.ResourceDownloadInfoDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.resource.ResourceEditRequest;
import com.bjfu.contest.pojo.vo.ResourceDownloadInfoVO;
import com.bjfu.contest.pojo.vo.ResourceVO;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.service.ResourceService;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequireLogin
    @PostMapping("/edit")
    public BaseResult<Void> edit(@Validated @RequestBody ResourceEditRequest request) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        resourceService.edit(request, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @DeleteMapping("/delete")
    public BaseResult<Void> edit(@NotNull(message = "资源id不能为空") Long resourceId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        resourceService.delete(resourceId, userDTO.getAccount());
        return BaseResult.success();
    }

    @RequireLogin
    @GetMapping("/getDownloadInfo")
    public BaseResult<ResourceDownloadInfoVO> getDownloadUrl(@NotNull(message = "资源id不能为空") Long resourceId) {
        UserDTO userDTO = UserInfoContextUtil.getUserInfo()
                .orElseThrow(() -> new AppException(ResultEnum.USER_CONTEXT_ERROR));
        ResourceDownloadInfoDTO downloadInfo = resourceService.getDownloadInfo(resourceId, userDTO.getAccount());
        return BaseResult.success(new ResourceDownloadInfoVO(downloadInfo));
    }

    @RequireLogin
    @GetMapping("/listAllByTarget")
    public BaseResult<List<ResourceVO>> listAllByTarget(@NotNull(message = "资源类型不能为空") ResourceTypeEnum type,
                                                                     @NotNull(message = "目标id不能为空") Long targetId) {
        List<ResourceDTO> list = resourceService.listAllByTarget(type, targetId);
        List<ResourceVO> resourceVOList = list.stream()
                .map(ResourceVO::new).collect(Collectors.toList());
        return BaseResult.success(resourceVOList);
    }

}
