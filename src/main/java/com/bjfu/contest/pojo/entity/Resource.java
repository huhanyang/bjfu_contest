package com.bjfu.contest.pojo.entity;

import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * 资源文件
 * @author warthog
 */
@Getter
@Setter
@Entity
public class Resource extends BaseEntity {
    /**
     * 资源类型
     */
    @Enumerated
    @Column(nullable=false)
    private ResourceTypeEnum type;
    /**
     * 内容类型
     */
    @Enumerated
    @Column(nullable=false)
    private ResourceContentTypeEnum contentType;
    /**
     * 创建人
     */
    @ManyToOne
    private User creator;
    /**
     * 所属目标
     */
    private Long targetId;
    /**
     * 文件名
     */
    @Column(length = 64, nullable=false)
    private String fileName;
    /**
     * 分类
     */
    @Column(length = 32, nullable = false)
    private String classification;
    /**
     * 内容
     */
    @Column(length = 256, nullable=false)
    private String content;
}
