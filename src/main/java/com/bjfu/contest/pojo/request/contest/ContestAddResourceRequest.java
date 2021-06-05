package com.bjfu.contest.pojo.request.contest;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ContestAddResourceRequest {
    /**
     * 竞赛id
     */
    @NotNull(message = "竞赛id不能为空")
    private Long contestId;
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
