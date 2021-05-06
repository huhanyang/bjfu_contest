package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.ContestDAO;
import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.entity.Contest;
import com.bjfu.contest.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}
