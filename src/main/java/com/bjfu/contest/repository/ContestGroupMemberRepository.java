package com.bjfu.contest.repository;


import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestGroupMember;
import com.bjfu.contest.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ContestGroupMemberRepository extends JpaRepository<ContestGroupMember, Long> {

    List<ContestGroupMember> findAllByMember(User user);

    List<ContestGroupMember> findAllByContestAndMember(Contest contest, User member);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select groupMember from ContestGroupMember groupMember where groupMember.contest=?1 and groupMember.member=?2")
    List<ContestGroupMember> findAllByContestAndMemberForUpdate(Contest contest, User member);
}
