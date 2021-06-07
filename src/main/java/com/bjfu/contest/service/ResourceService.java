package com.bjfu.contest.service;

import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.dto.ResourceDownloadInfoDTO;
import com.bjfu.contest.pojo.request.resource.ResourceEditRequest;
import com.bjfu.contest.pojo.request.resource.ResourceUploadRequest;

import java.util.List;

/**
 * 资源相关操作服务
 * @author warthog
 */
public interface ResourceService {

    /**
     * 上传资源
     * @param request 请求
     * @param account 操作人账户
     * @return 资源
     */
    ResourceDTO upload(ResourceUploadRequest request, String account);

    /**
     * 编辑资源
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
     * @param account 操作人账号
     * @return 根据分类名分类后的资源列表
     */
    List<ResourceDTO> listAllByTarget(ResourceTypeEnum type, Long targetId, String account);

}
