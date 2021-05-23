package com.bjfu.contest.controller;

import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.AppException;
import com.bjfu.contest.pojo.BaseResult;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.resource.ResourceEditRequest;
import com.bjfu.contest.pojo.vo.ResourceVO;
import com.bjfu.contest.security.annotation.RequireLogin;
import com.bjfu.contest.service.ResourceService;
import com.bjfu.contest.utils.UserInfoContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
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
    @GetMapping("/getDownloadUrl")
    public BaseResult<String> getDownloadUrl(@NotNull(message = "资源id不能为空") Long resourceId) {
        String url = resourceService.getDownloadUrl(resourceId);
        return BaseResult.success(url);
    }

    @RequireLogin
    @GetMapping("/listAllByTarget")
    public BaseResult<Map<String, List<ResourceVO>>> listAllByTarget(@NotNull(message = "资源类型不能为空") ResourceTypeEnum type,
                                                  @NotNull(message = "目标id不能为空") Long targetId) {
        List<ResourceDTO> list = resourceService.listAllByTarget(type, targetId);
        Map<String, List<ResourceVO>> map = list.stream()
                .map(ResourceVO::new)
                .collect(Collectors.groupingBy(ResourceVO::getClassification));
        return BaseResult.success(map);
    }

}
