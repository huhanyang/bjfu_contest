package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestTeacherRepository extends JpaRepository<ContestTeacher, Long> {

}
