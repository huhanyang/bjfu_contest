package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.ContestGroup;
import com.bjfu.contest.pojo.entity.ContestProcess;
import com.bjfu.contest.pojo.entity.ContestProcessGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestProcessGroupRepository extends JpaRepository<ContestProcessGroup, Long> {

}
