package com.bjfu.contest.dao;


import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContestRegisterDAO {
    ContestRegister insert(ContestRegister register);
    void delete(ContestRegister register);
    ContestRegister update(ContestRegister register);
    Optional<ContestRegister> findByContestAndUser(Contest contest, User user);
    Optional<ContestRegister> findByContestAndUserForUpdate(Contest contest, User user);
    Page<ContestRegister> findAllByContestAndStatusInAndRegisterLike(Contest contest,
                                                                     List<ContestRegisterStatusEnum> statuses,
                                                                     String registerName,
                                                                     String registerGrade,
                                                                     String registerCollege,
                                                                     String registerMajor,
                                                                     BasePageAndSorterRequest.Pagination pagination,
                                                                     List<BasePageAndSorterRequest.Sorter> sorter);
    List<ContestRegister> findAllByUser(User user);
}
