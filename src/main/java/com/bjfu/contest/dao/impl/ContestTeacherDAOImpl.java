package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.ContestTeacherDAO;
import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestTeacher;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import com.bjfu.contest.repository.ContestTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContestTeacherDAOImpl implements ContestTeacherDAO {

    @Autowired
    private ContestTeacherRepository contestTeacherRepository;


    @Override
    public ContestTeacher insert(ContestTeacher contestTeacher) {
        return contestTeacherRepository.save(contestTeacher);
    }

    @Override
    public List<ContestTeacher> insertAll(List<ContestTeacher> contestTeachers) {
        return contestTeacherRepository.saveAll(contestTeachers);
    }

    @Override
    public void delete(ContestTeacher contestTeacher) {
        contestTeacherRepository.delete(contestTeacher);
    }

    @Override
    public ContestTeacher update(ContestTeacher contestTeacher) {
        return contestTeacherRepository.save(contestTeacher);
    }

    @Override
    public Optional<ContestTeacher> findByContestAndTeacher(Contest contest, User teacher) {
        return contestTeacherRepository.findByContestAndTeacher(contest, teacher);
    }

    @Override
    public Optional<ContestTeacher> findByContestAndTeacherForUpdate(Contest contest, User teacher) {
        return contestTeacherRepository.findByContestAndTeacherForUpdate(contest, teacher);
    }

    @Override
    public List<ContestTeacher> findByContestAndTeachersInForUpdate(Contest contest, List<User> teachers) {
        return contestTeacherRepository.findByContestAndTeachersInForUpdate(contest, teachers);
    }

    @Override
    public List<ContestTeacher> findAllByContest(Contest contest) {
        return contestTeacherRepository.findAllByContest(contest);
    }

    @Override
    public List<ContestTeacher> findAllByTeacher(User teacher) {
        return contestTeacherRepository.findAllByTeacher(teacher);
    }

    @Override
    public Page<ContestTeacher> findAllByTeacherAndContestNameLikeAndContestStatusInAndCreatorNameLikeAndCreatorCollegeLike(User teacher,
                                                                                                                            String contestName,
                                                                                                                            List<ContestStatusEnum> contestStatus,
                                                                                                                            String creatorName,
                                                                                                                            String creatorCollege,
                                                                                                                            BasePageAndSorterRequest.Pagination pagination,
                                                                                                                            List<BasePageAndSorterRequest.Sorter> sorter) {
        List<Sort.Order> orders = Optional.ofNullable(sorter).orElse(new LinkedList<>())
                .stream()
                .filter(singleSorter -> StringUtils.hasText(singleSorter.getField()))
                .map(singleSorter -> new Sort.Order(singleSorter.getOrder(), singleSorter.getField()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pagination.getCurrent() - 1, pagination.getPageSize(), Sort.by(orders));
        return contestTeacherRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("teacher"), teacher));
            if (!CollectionUtils.isEmpty(contestStatus)) {
                predicates.add(cb.and(root.get("contest").get("status").in(contestStatus)));
            }
            predicates.add(cb.notEqual(root.get("contest").get("status"), ContestStatusEnum.DELETE));
            if(StringUtils.hasText(contestName)) {
                predicates.add(cb.like(root.get("contest").get("name"), "%" + contestName + "%"));
            }
            if(StringUtils.hasText(creatorName)) {
                predicates.add(cb.like(root.get("contest").get("creator").get("name"), "%" + creatorName + "%"));
            }
            if(StringUtils.hasText(creatorCollege)) {
                predicates.add(cb.like(root.get("contest").get("creator").get("college"), "%" + creatorCollege + "%"));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, pageRequest);
    }
}
