package com.bjfu.contest.pojo.request.resource;

import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ResourceUploadRequest {
    /**
     * 目标实体id
     */
    @NotNull(message = "目标实体id不能为空")
    private Long targetId;
    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    ResourceTypeEnum type;
    /**
     * 资源内容类型
     */
    @NotNull(message = "资源内容类型不能为空")
    ResourceContentTypeEnum contentType;
    /**
     * 文件名
     */
    @NotEmpty(message = "文件名不能为空")
    @Length(min = 1, max = 64, message = "文件名最长64")
    private String fileName;
    /**
     * 文件分类名
     */
    @NotEmpty(message = "文件分类名不能为空")
    @Length(min = 1, max = 32, message = "分类名最长32")
    private String classification;
    /**
     * 文件
     */
    @NotNull(message = "文件不能为空!")
    private MultipartFile file;
}
