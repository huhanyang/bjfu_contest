package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.entity.Resource;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ResourceDownloadInfoDTO {

    public ResourceDownloadInfoDTO() {

    }

    public ResourceDownloadInfoDTO(Resource resource, String url) {
        if(resource != null) {
            BeanUtils.copyProperties(resource, this);
            this.url = url;
        }
    }

    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date lastModifiedTime;
    /**
     * 内容类型
     */
    private ResourceContentTypeEnum contentType;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 分类
     */
    private String classification;
    /**
     * 下载用url
     */
    private String url;

}
