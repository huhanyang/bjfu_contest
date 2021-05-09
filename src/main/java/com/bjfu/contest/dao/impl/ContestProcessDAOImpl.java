package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.ContestProcessDAO;
import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.pojo.entity.ContestProcess;
import com.bjfu.contest.repository.ContestProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class ContestProcessDAOImpl implements ContestProcessDAO {

    @Autowired
    private ContestProcessRepository contestProcessRepository;

    private static List<ContestProcessStatusEnum> EXIST_STATUS = new LinkedList<>();

    static {
        EXIST_STATUS.add(ContestProcessStatusEnum.CREATING);
        EXIST_STATUS.add(ContestProcessStatusEnum.RUNNING);
        EXIST_STATUS.add(ContestProcessStatusEnum.FINISH);
    }

    @Override
    public ContestProcess insert(ContestProcess process) {
        process.setStatus(ContestProcessStatusEnum.CREATING);
        return contestProcessRepository.save(process);
    }

    @Override
    public void delete(ContestProcess process) {
        process.setStatus(ContestProcessStatusEnum.DELETE);
        contestProcessRepository.save(process);
    }

    @Override
    public ContestProcess update(ContestProcess process) {
        return contestProcessRepository.save(process);
    }

    @Override
    public Optional<ContestProcess> findById(Long id) {
        return contestProcessRepository.findByIdAndStatusIn(id, EXIST_STATUS);
    }

    @Override
    public Optional<ContestProcess> findByIdForUpdate(Long id) {
        return contestProcessRepository.findByIdAndStatusInForUpdate(id, EXIST_STATUS);
    }
}
