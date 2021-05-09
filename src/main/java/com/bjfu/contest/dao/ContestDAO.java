package com.bjfu.contest.dao;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContestDAO {
    Contest insert(Contest contest);
    void delete(Contest contest);
    Contest update(Contest contest);
    Optional<Contest> findById(Long id);
    Optional<Contest> findByIdForUpdate(Long id);
    Page<Contest> findByCreatorAndNameLikeAndStatusIn(User creator,
                                                      String name,
                                                      List<ContestStatusEnum> statuses,
                                                      BasePageAndSorterRequest.Pagination pagination,
                                                      List<BasePageAndSorterRequest.Sorter> sorter);
    Page<Contest> findByNameLikeAndStatusInAndCreatorNameLikeAndCreatorCollegeLike(String name,
                                                                                   List<ContestStatusEnum> statuses,
                                                                                   String creatorName,
                                                                                   String creatorCollege,
                                                                                   BasePageAndSorterRequest.Pagination pagination,
                                                                                   List<BasePageAndSorterRequest.Sorter> sorter);
}
