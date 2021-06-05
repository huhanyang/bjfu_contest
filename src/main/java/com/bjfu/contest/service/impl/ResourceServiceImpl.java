package com.bjfu.contest.service.impl;

import com.bjfu.contest.config.MinioConfig;
import com.bjfu.contest.dao.ResourceDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ResourceContentTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.dto.ResourceDownloadInfoDTO;
import com.bjfu.contest.pojo.entity.Resource;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.resource.ResourceEditRequest;
import com.bjfu.contest.service.OssService;
import com.bjfu.contest.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OssService ossService;
    @Autowired
    private ResourceDAO resourceDAO;

    private static final Set<ResourceTypeEnum> ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES = new HashSet<>();

    static {
        ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.add(ResourceTypeEnum.ALL);
        ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.add(ResourceTypeEnum.USER);
        ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.add(ResourceTypeEnum.TEACHER);
        ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.add(ResourceTypeEnum.STUDENT);
        ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.add(ResourceTypeEnum.CONTEST);
        ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.add(ResourceTypeEnum.PROCESS);
    }

    @Override
    @Transactional
    public ResourceDTO create(User creator, Long targetId, String fileName, ResourceTypeEnum type, ResourceContentTypeEnum contentType, String classification, InputStream stream) {
        // 上传oss
        String objectName = UUID.randomUUID().toString();
        ossService.putObject(MinioConfig.FILE_BUCKET_NAME, objectName, stream);
        // 保存资源信息
        Resource resource = new Resource();
        resource.setCreator(creator);
        resource.setTargetId(targetId);
        resource.setFileName(fileName);
        resource.setType(type);
        resource.setContentType(contentType);
        resource.setClassification(classification);
        resource.setContent(objectName);
        resourceDAO.insert(resource);
        return new ResourceDTO(resource, true);
    }

    @Override
    @Transactional
    public void edit(ResourceEditRequest request, String account) {
        // 查找Resource
        Resource resource = resourceDAO.findById(request.getResourceId())
                .orElseThrow(() -> new BizException(ResultEnum.RESOURCE_NOT_EXIST));
        // 资源创建人验证
        if(!resource.getCreator().getAccount().equals(account)) {
            // todo 根据类型和targetId来判断是否有权限
            throw new BizException(ResultEnum.NOT_RESOURCE_CREATOR);
        }
        // 更新并保存Resource
        resource.setClassification(request.getClassification());
        resource.setFileName(request.getFileName());
        resourceDAO.update(resource);
    }

    @Override
    @Transactional
    public void delete(Long resourceId, String account) {
        // 查找Resource
        Resource resource = resourceDAO.findById(resourceId)
                .orElseThrow(() -> new BizException(ResultEnum.RESOURCE_NOT_EXIST));
        // 资源创建人验证
        if(!resource.getCreator().getAccount().equals(account)) {
            // todo 根据类型和targetId来判断是否有权限
            throw new BizException(ResultEnum.NOT_RESOURCE_CREATOR);
        }
        // 删除oss中的文件
        ossService.deleteObject(MinioConfig.FILE_BUCKET_NAME, resource.getContent());
        // 删除Resource记录
        resourceDAO.delete(resource);
    }

    @Override
    public ResourceDownloadInfoDTO getDownloadInfo(Long resourceId, String account) {
        // 查找Resource
        Resource resource = resourceDAO.findById(resourceId)
                .orElseThrow(() -> new BizException(ResultEnum.RESOURCE_NOT_EXIST));
        // 验证是否可以直接访问
        if(!ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.contains(resource.getType())) {
            throw new BizException(ResultEnum.CANT_ACCESS_RESOURCE);
        }
        // 生成下载url
        String url = ossService.preSignedGetObject(MinioConfig.FILE_BUCKET_NAME, resource.getContent());
        return new ResourceDownloadInfoDTO(resource, url);
    }

    @Override
    public List<ResourceDTO> listAllByTarget(ResourceTypeEnum type, Long targetId) {
        if(!ANY_USER_CAN_LIST_AND_DOWNLOAD_TYPES.contains(type)) {
            throw new BizException(ResultEnum.CANT_ACCESS_RESOURCE);
        }
        // 获取实体下的所有资源
        List<Resource> resources = resourceDAO.findAllByTypeAndTargetId(type, targetId);
        // 按照分类进行分组
        return resources.stream()
                .map(resource -> new ResourceDTO(resource, true))
                .collect(Collectors.toList());
    }
}
