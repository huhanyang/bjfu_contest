package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
