package com.bjfu.contest.dao;

import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.entity.Resource;

import java.util.List;
import java.util.Optional;

public interface ResourceDAO {
    Resource insert(Resource resource);
    void delete(Resource resource);
    Resource update(Resource resource);
    Optional<Resource> findById(Long resourceId);
    List<Resource> findAllByTypeAndTargetId(ResourceTypeEnum type, Long targetId);
}
