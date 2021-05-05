package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ContestRepository extends JpaRepository<Contest, Long> {

}
