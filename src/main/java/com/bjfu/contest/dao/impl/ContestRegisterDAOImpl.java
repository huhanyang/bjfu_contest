package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.ContestRegisterDAO;
import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.pojo.entity.ContestRegister;
import com.bjfu.contest.pojo.entity.User;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import com.bjfu.contest.repository.ContestRegisterRepository;
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
public class ContestRegisterDAOImpl implements ContestRegisterDAO {

    @Autowired
    private ContestRegisterRepository contestRegisterRepository;

    private static final List<ContestRegisterStatusEnum> EXIST_STATUS = new LinkedList<>();

    static {
        EXIST_STATUS.add(ContestRegisterStatusEnum.SIGN_UP);
        EXIST_STATUS.add(ContestRegisterStatusEnum.BAN);
    }

    @Override
    public ContestRegister insert(ContestRegister register) {
        register.setStatus(ContestRegisterStatusEnum.SIGN_UP);
        return contestRegisterRepository.save(register);
    }

    @Override
    public void delete(ContestRegister register) {
        register.setStatus(ContestRegisterStatusEnum.DELETE);
        contestRegisterRepository.save(register);
    }

    @Override
    public ContestRegister update(ContestRegister register) {
        return contestRegisterRepository.save(register);
    }

    @Override
    public Optional<ContestRegister> findByContestAndUser(Contest contest, User user) {
        return contestRegisterRepository.findByContestAndUserAndStatusIn(contest, user, EXIST_STATUS);
    }

    @Override
    public Optional<ContestRegister> findByContestAndUserForUpdate(Contest contest, User user) {
        return contestRegisterRepository.findByContestAndUserAndStatusInForUpdate(contest, user, EXIST_STATUS);
    }

    private static final Map<String, Integer> FIELD_ORDER_WEIGHT = new HashMap<>();

    static {
        FIELD_ORDER_WEIGHT.put("status", 1);
        FIELD_ORDER_WEIGHT.put("createdTime", 2);
    }

    @Override
    public Page<ContestRegister> findAllByContestAndStatusInAndRegisterLike(Contest contest,
                                                                            List<ContestRegisterStatusEnum> statuses,
                                                                            String registerName,
                                                                            String registerGrade,
                                                                            String registerCollege,
                                                                            String registerMajor,
                                                                            BasePageAndSorterRequest.Pagination pagination,
                                                                            List<BasePageAndSorterRequest.Sorter> sorter) {
        List<Sort.Order> orders = Optional.ofNullable(sorter).orElse(new LinkedList<>())
                .stream()
                .filter(singleSorter -> StringUtils.hasText(singleSorter.getField()))
                .sorted(Comparator.comparingInt(s -> FIELD_ORDER_WEIGHT.get(s.getField())))
                .map(singleSorter -> new Sort.Order(singleSorter.getOrder(), singleSorter.getField()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(pagination.getCurrent() - 1, pagination.getPageSize(), Sort.by(orders));
        return contestRegisterRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!CollectionUtils.isEmpty(statuses)) {
                predicates.add(cb.and(root.get("status").in(statuses)));
            }
            predicates.add(cb.notEqual(root.get("status"), ContestRegisterStatusEnum.DELETE));
            if(StringUtils.hasText(registerName)) {
                predicates.add(cb.like(root.get("user").get("name"), "%" + registerName + "%"));
            }
            if(StringUtils.hasText(registerGrade)) {
                predicates.add(cb.like(root.get("user").get("grade"), "%" + registerGrade + "%"));
            }
            if(StringUtils.hasText(registerCollege)) {
                predicates.add(cb.like(root.get("user").get("college"), "%" + registerCollege + "%"));
            }
            if(StringUtils.hasText(registerMajor)) {
                predicates.add(cb.like(root.get("user").get("major"), "%" + registerMajor + "%"));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, pageRequest);
    }

    @Override
    public List<ContestRegister> findAllByUser(User user) {
        return contestRegisterRepository.findAllByUserAndStatusIn(user, EXIST_STATUS);
    }
}
