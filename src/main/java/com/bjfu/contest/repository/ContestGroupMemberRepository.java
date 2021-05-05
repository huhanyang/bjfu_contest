package com.bjfu.contest.repository;


import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestGroupMember;
import com.bjfu.contest.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestGroupMemberRepository extends JpaRepository<ContestGroupMember, Long> {

}
