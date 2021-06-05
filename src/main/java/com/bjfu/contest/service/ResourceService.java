package com.bjfu.contest.service;

import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.dto.ResourceDownloadInfoDTO;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.resource.ResourceEditRequest;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 资源相关操作服务
 * @author warthog
 */
public interface ResourceService {

    /**
     * 创建资源
     * @param creator 创建者
     * @param targetId 依赖的实体id
     * @param fileName 文件名
     * @param type 资源类型
     * @param contentType 内容类型
     * @param classification 分类名
     * @param stream 文件输入流
     * @return 创建后的资源
     */
    ResourceDTO create(User creator, Long targetId, String fileName, ResourceTypeEnum type, ResourceContentTypeEnum contentType, String classification, InputStream stream);

    /**
     * 编辑资源信息
     * @param request 请求
     * @param account 操作人账号
     */
    void edit(ResourceEditRequest request, String account);

    /**
     * 删除资源
     * @param resourceId 资源id
     * @param account 操作人账号
     */
    void delete(Long resourceId, String account);

    /**
     * 获取资源的下载链接
     * @param resourceId 资源id
     * @param account 操作人账号
     * @return 下载信息
     */
    ResourceDownloadInfoDTO getDownloadInfo(Long resourceId, String account);

    /**
     * 列出目标实体的所有资源
     * @param type 目标实体类型
     * @param targetId 目标实体id
     * @return 根据分类名分类后的资源列表
     */
    List<ResourceDTO> listAllByTarget(ResourceTypeEnum type, Long targetId);

}
