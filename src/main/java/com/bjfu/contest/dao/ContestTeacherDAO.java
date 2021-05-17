package com.bjfu.contest.dao;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestTeacher;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContestTeacherDAO {
    ContestTeacher insert(ContestTeacher contestTeacher);
    List<ContestTeacher> insertAll(List<ContestTeacher> contestTeachers);
    void delete(ContestTeacher contestTeacher);
    ContestTeacher update(ContestTeacher contestTeacher);
    Optional<ContestTeacher> findByContestAndTeacher(Contest contest, User teacher);
    Optional<ContestTeacher> findByContestAndTeacherForUpdate(Contest contest, User teacher);
    Optional<ContestTeacher> findByContestAndTeacherInForUpdate(Contest contest, List<User> teachers);
    List<ContestTeacher> findAllByContest(Contest contest);
    List<ContestTeacher> findAllByTeacher(User teacher);
    Page<ContestTeacher> findAllByTeacherAndContestNameLikeAndContestStatusInAndCreatorNameLikeAndCreatorCollegeLike(User teacher,
                                                                                                                     String contestName,
                                                                                                                     List<ContestStatusEnum> contestStatus,
                                                                                                                     String creatorName,
                                                                                                                     String creatorCollege,
                                                                                                                     BasePageAndSorterRequest.Pagination pagination,
                                                                                                                     List<BasePageAndSorterRequest.Sorter> sorter);

}
