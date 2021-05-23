package com.bjfu.contest.repository;

import com.bjfu.contest.enums.ResourceTypeEnum;
import com.bjfu.contest.pojo.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findAllByTypeAndTargetId(ResourceTypeEnum type, Long targetId);
}
