package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.ContestTeacher;
import com.bjfu.contest.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ContestTeacherRepository extends JpaRepository<ContestTeacher, Long>, JpaSpecificationExecutor<ContestTeacher> {
    Optional<ContestTeacher> findByContestAndTeacher(Contest contest, User teacher);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select teacher from ContestTeacher teacher where teacher.contest=?1 and teacher.teacher =?2")
    Optional<ContestTeacher> findByContestAndTeacherForUpdate(Contest contest, User teacher);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select teacher from ContestTeacher teacher where teacher.contest=?1 and teacher.teacher in ?2")
    Optional<ContestTeacher> findByContestAndTeacherInForUpdate(Contest contest, List<User> teacher);

    List<ContestTeacher> findAllByContest(Contest contest);
    List<ContestTeacher> findAllByTeacher(User teacher);
}
