package com.bjfu.contest.service.impl;

import com.bjfu.contest.config.MinioConfig;
import com.bjfu.contest.dao.ResourceDAO;
import com.bjfu.contest.dao.UserDAO;
import com.bjfu.contest.enums.ResourceOperateTypeEnum;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.exception.BizException;
import com.bjfu.contest.pojo.dto.ResourceDTO;
import com.bjfu.contest.pojo.dto.ResourceDownloadInfoDTO;
import com.bjfu.contest.pojo.entity.Resource;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.resource.ResourceEditRequest;
import com.bjfu.contest.pojo.request.resource.ResourceUploadRequest;
import com.bjfu.contest.service.OssService;
import com.bjfu.contest.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private OssService ossService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ResourceDAO resourceDAO;

    @Override
    @Transactional
    public ResourceDTO upload(ResourceUploadRequest request, String account) {
        // 获取用户信息并鉴权
        User creator = userDAO.findByAccount(account)
                .orElseThrow(() -> new BizException(ResultEnum.USER_DONT_EXIST));
        checkAuth(request.getType(), request.getTargetId(), ResourceOperateTypeEnum.UPLOAD, account);
        // 上传oss
        String objectName = UUID.randomUUID().toString();
        try {
            ossService.putObject(MinioConfig.FILE_BUCKET_NAME, objectName, request.getFile().getInputStream());
        } catch (IOException e) {
            log.error("获取文件上传流失败", e);
            throw new BizException(ResultEnum.GET_FILE_INPUT_STREAM_FAILED);
        }
        // 保存资源信息
        Resource resource = new Resource();
        resource.setCreator(creator);
        resource.setTargetId(request.getTargetId());
        resource.setFileName(request.getFileName());
        resource.setType(request.getType());
        resource.setContentType(request.getContentType());
        resource.setClassification(request.getClassification());
        resource.setContent(objectName);
        resourceDAO.insert(resource);
        return new ResourceDTO(resource, true);
    }

    @Override
    @Transactional
    public void edit(ResourceEditRequest request, String account) {
        // 查找Resource并鉴权
        Resource resource = resourceDAO.findById(request.getResourceId())
                .orElseThrow(() -> new BizException(ResultEnum.RESOURCE_NOT_EXIST));
        checkAuth(resource.getType(), resource.getTargetId(), ResourceOperateTypeEnum.EDIT, account);
        // 更新并保存Resource
        resource.setClassification(request.getClassification());
        resource.setFileName(request.getFileName());
        resourceDAO.update(resource);
    }

    @Override
    @Transactional
    public void delete(Long resourceId, String account) {
        // 查找Resource并鉴权
        Resource resource = resourceDAO.findById(resourceId)
                .orElseThrow(() -> new BizException(ResultEnum.RESOURCE_NOT_EXIST));
        checkAuth(resource.getType(), resource.getTargetId(), ResourceOperateTypeEnum.DELETE, account);
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
        checkAuth(resource.getType(), resource.getTargetId(), ResourceOperateTypeEnum.DOWNLOAD, account);
        // 生成下载url
        String url = ossService.preSignedGetObject(MinioConfig.FILE_BUCKET_NAME, resource.getContent());
        return new ResourceDownloadInfoDTO(resource, url);
    }

    @Override
    public List<ResourceDTO> listAllByTarget(ResourceTypeEnum type, Long targetId, String account) {
        checkAuth(type, targetId, ResourceOperateTypeEnum.LIST, account);
        // 获取实体下的所有资源
        List<Resource> resources = resourceDAO.findAllByTypeAndTargetId(type, targetId);
        // 按照分类进行分组
        return resources.stream()
                .map(resource -> new ResourceDTO(resource, true))
                .collect(Collectors.toList());
    }

    void checkAuth(ResourceTypeEnum type, Long targetId, ResourceOperateTypeEnum operateType, String account) {
        // todo 鉴权逻辑
    }

}
