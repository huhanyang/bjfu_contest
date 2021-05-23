package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Optional;

@Data
public class ResourceVO {

    public ResourceVO() {

    }

    public ResourceVO(ResourceDTO resourceDTO) {
        BeanUtils.copyProperties(resourceDTO, this, "creator");
        this.creator = Optional.ofNullable(resourceDTO.getCreator()).map(UserVO::new).orElse(null);
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
    private UserVO creator;
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
