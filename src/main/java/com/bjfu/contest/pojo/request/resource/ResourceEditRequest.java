package com.bjfu.contest.pojo.request.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResourceEditRequest {
    /**
     * 资源id
     */
    @NotNull(message = "资源id不能为空")
    private Long resourceId;
    /**
     * 文件分类名
     */
    @NotBlank(message = "分类名不能为空")
    private String classification;
    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;
}
