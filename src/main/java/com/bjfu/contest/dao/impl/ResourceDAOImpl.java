package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.ResourceDAO;
import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.entity.Resource;
import com.bjfu.contest.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ResourceDAOImpl implements ResourceDAO {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource insert(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public void delete(Resource resource) {
        resourceRepository.delete(resource);
    }

    @Override
    public Resource update(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Optional<Resource> findById(Long resourceId) {
        return resourceRepository.findById(resourceId);
    }

    @Override
    public List<Resource> findAllByTypeAndTargetId(ResourceTypeEnum type, Long targetId) {
        return resourceRepository.findAllByTypeAndTargetId(type, targetId);
    }
}
