package com.bjfu.contest.repository;


import com.bjfu.contest.pojo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ContestGroupMemberRepository extends JpaRepository<ContestGroupMember, Long> {

    @Query(value = "select groupMember from ContestGroupMember groupMember left join ContestRegister register on register.user = ?1 and groupMember.member = register")
    List<ContestGroupMember> findAllByMember(User member);

    List<ContestGroupMember> findAllByContestAndMember(Contest contest, ContestRegister member);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select groupMember from ContestGroupMember groupMember where groupMember.contest=?1 and groupMember.member=?2")
    List<ContestGroupMember> findAllByContestAndMemberForUpdate(Contest contest, ContestRegister member);

    void deleteByContestAndGroupAndMember(Contest contest, ContestGroup group, ContestRegister member);
}
