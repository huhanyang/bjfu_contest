package com.bjfu.contest.pojo.request.contest;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ContestAddResourceRequest {
    /**
     * 竞赛id
     */
    private Long contestId;
    /**
     * 文件名
     */
    @NotEmpty(message = "文件名不能为空")
    private String fileName;
    /**
     * 文件分类名
     */
    @NotEmpty(message = "文件分类名不能为空")
    private String classification;
    /**
     * 文件
     */
    @NotNull(message = "文件不能为空!")
    private MultipartFile file;
}
