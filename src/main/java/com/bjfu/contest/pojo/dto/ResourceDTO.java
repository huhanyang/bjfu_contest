package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.entity.Resource;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ResourceDTO {

    public ResourceDTO() {}

    public ResourceDTO(Resource resource, boolean needCreator) {
        if(resource != null) {
            BeanUtils.copyProperties(resource, this);
            if(needCreator) {
                this.creator = new UserDTO(resource.getCreator());
            }
        }
    }


    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date lastModifiedTime;

    /**
     * 资源类型
     */
    private ResourceTypeEnum type;
    /**
     * 内容类型
     */
    private ResourceContentTypeEnum contentType;
    /**
     * 创建人
     */
    private UserDTO creator;
    /**
     * 所属目标
     */
    private Long targetId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 分类
     */
    private String classification;
    /**
     * 内容
     */
    private String content;
}
