package com.bjfu.contest.repository;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestRegisterRepository extends JpaRepository<ContestRegister, Long> {

}
