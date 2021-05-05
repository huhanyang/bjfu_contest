package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestProcessRepository extends JpaRepository<ContestProcess, Long> {

}
