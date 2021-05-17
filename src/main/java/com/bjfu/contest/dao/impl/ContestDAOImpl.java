package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import com.bjfu.contest.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ContestDAOImpl implements ContestDAO {

    @Autowired
    private ContestRepository contestRepository;

    private static final List<ContestStatusEnum> STATUS_ENUMS = new ArrayList<>();

    static {
        STATUS_ENUMS.add(ContestStatusEnum.CREATING);
        STATUS_ENUMS.add(ContestStatusEnum.REGISTERING);
        STATUS_ENUMS.add(ContestStatusEnum.RUNNING);
        STATUS_ENUMS.add(ContestStatusEnum.FINISH);
    }

    @Override
    public Contest insert(Contest contest) {
        contest.setStatus(ContestStatusEnum.CREATING);
        return contestRepository.save(contest);
    }

    @Override
    public void delete(Contest contest) {
        contest.setStatus(ContestStatusEnum.DELETE);
        contestRepository.save(contest);
    }

    @Override
    public Contest update(Contest contest) {
        return contestRepository.save(contest);
    }

    @Override
    public Optional<Contest> findById(Long id) {
        return contestRepository.findByIdAndStatusIn(id, STATUS_ENUMS);
    }

    @Override
    public Optional<Contest> findByIdForUpdate(Long id) {
        return contestRepository.findByIdAndStatusInForUpdate(id, STATUS_ENUMS);
    }

    private static final Map<String, Integer> FIELD_ORDER_WEIGHT = new HashMap<>();

    static {
        FIELD_ORDER_WEIGHT.put("name", 1);
        FIELD_ORDER_WEIGHT.put("status", 2);
        FIELD_ORDER_WEIGHT.put("groupMemberCount", 3);
        FIELD_ORDER_WEIGHT.put("createdTime", 4);
    }

    @Override
    public Page<Contest> findByCreatorAndNameLikeAndStatusIn(User creator,
                                                             String name,
                                                             List<ContestStatusEnum> statuses,
                                                             BasePageAndSorterRequest.Pagination pagination,
                                                             List<BasePageAndSorterRequest.Sorter> sorter) {
        List<Sort.Order> orders = Optional.ofNullable(sorter).orElse(new LinkedList<>())
                .stream()
                .filter(singleSorter -> StringUtils.hasText(singleSorter.getField()))
                .sorted(Comparator.comparingInt(s -> FIELD_ORDER_WEIGHT.get(s.getField())))
                .map(singleSorter -> new Sort.Order(singleSorter.getOrder(), singleSorter.getField()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pagination.getCurrent() - 1, pagination.getPageSize(), Sort.by(orders));
        return contestRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("creator"), creator));
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (!CollectionUtils.isEmpty(statuses)) {
                predicates.add(cb.and(root.get("status").in(statuses)));
            }
            predicates.add(cb.notEqual(root.get("status"), ContestStatusEnum.DELETE));
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, pageRequest);
    }

    @Override
    public Page<Contest> findByNameLikeAndStatusInAndCreatorNameLikeAndCreatorCollegeLike(String name,
                                                                                          List<ContestStatusEnum> statuses,
                                                                                          String creatorName,
                                                                                          String creatorCollege,
                                                                                          BasePageAndSorterRequest.Pagination pagination,
                                                                                          List<BasePageAndSorterRequest.Sorter> sorter) {
        List<Sort.Order> orders = Optional.ofNullable(sorter).orElse(new LinkedList<>())
                .stream()
                .filter(singleSorter -> StringUtils.hasText(singleSorter.getField()))
                .sorted(Comparator.comparingInt(s -> FIELD_ORDER_WEIGHT.get(s.getField())))
                .map(singleSorter -> new Sort.Order(singleSorter.getOrder(), singleSorter.getField()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pagination.getCurrent() - 1, pagination.getPageSize(), Sort.by(orders));
        return contestRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (!CollectionUtils.isEmpty(statuses)) {
                predicates.add(cb.and(root.get("status").in(statuses)));
            }
            predicates.add(cb.notEqual(root.get("status"), ContestStatusEnum.DELETE));
            if(StringUtils.hasText(creatorName)) {
                predicates.add(cb.like(root.get("creator").get("name"), "%" + creatorName + "%"));
            }
            if(StringUtils.hasText(creatorCollege)) {
                predicates.add(cb.like(root.get("creator").get("college"), "%" + creatorCollege + "%"));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, pageRequest);
    }
}
