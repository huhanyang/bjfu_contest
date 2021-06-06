package com.bjfu.contest.dao.impl;

import com.bjfu.contest.dao.CollegeMajorGradeClassDAO;
import com.bjfu.contest.pojo.dto.CollegeMajorGradeClassDTO;
import com.bjfu.contest.pojo.entity.CollegeMajorGradeClass;
import com.bjfu.contest.repository.CollegeMajorGradeClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CollegeMajorGradeClassDAOImpl implements CollegeMajorGradeClassDAO {

    @Autowired
    private CollegeMajorGradeClassRepository collegeMajorGradeClassRepository;

    @Override
    public List<String> findAllCollege() {
        return collegeMajorGradeClassRepository.findAllByMajorIsNull()
                .stream()
                .map(CollegeMajorGradeClass::getCollege)
                .collect(Collectors.toList());
    }

    @Override
    public List<CollegeMajorGradeClass> findAllClassByCollege(String college) {
        return collegeMajorGradeClassRepository.findAllByCollegeAndCompanyIsNotNull(college);
    }

    @Override
    public Optional<CollegeMajorGradeClass> findCollegeForUpdate(String college) {
        return collegeMajorGradeClassRepository.findByCollegeAndMajorIsNullForUpdate(college);
    }

    @Override
    public Optional<CollegeMajorGradeClass> findMajorForUpdate(String college, String major) {
        return collegeMajorGradeClassRepository.findByCollegeAndMajorAndGradeIsNull(college, major);
    }

    @Override
    public Optional<CollegeMajorGradeClass> findGradeForUpdate(String college, String major, String grade) {
        return collegeMajorGradeClassRepository.findByCollegeAndMajorAndGradeAndCompanyIsNull(college, major, grade);
    }

    @Override
    public Optional<CollegeMajorGradeClass> findCompanyForUpdate(String college, String major, String grade, String company) {
        return collegeMajorGradeClassRepository.findByCollegeAndMajorAndGradeAndCompany(college, major, grade, company);
    }

    @Override
    public CollegeMajorGradeClass insertCollegeIfNotExist(String college) {
        CollegeMajorGradeClass collegeEntity = findCollegeForUpdate(college)
                .orElse(null);
        if(collegeEntity == null) {
            collegeEntity = new CollegeMajorGradeClass();
            collegeEntity.setCollege(college);
            collegeMajorGradeClassRepository.save(collegeEntity);
        }
        return collegeEntity;
    }

    @Override
    public CollegeMajorGradeClass insertMajorIfNotExist(String college, String major) {
        CollegeMajorGradeClass collegeEntity = findMajorForUpdate(college, major)
                .orElse(null);
        if(collegeEntity == null) {
            collegeEntity = new CollegeMajorGradeClass();
            collegeEntity.setCollege(college);
            collegeEntity.setMajor(major);
            collegeMajorGradeClassRepository.save(collegeEntity);
        }
        return collegeEntity;
    }

    @Override
    public CollegeMajorGradeClass insertGradeIfNotExist(String college, String major, String grade) {
        CollegeMajorGradeClass collegeEntity = findGradeForUpdate(college, major, grade)
                .orElse(null);
        if(collegeEntity == null) {
            collegeEntity = new CollegeMajorGradeClass();
            collegeEntity.setCollege(college);
            collegeEntity.setMajor(major);
            collegeEntity.setGrade(grade);
            collegeMajorGradeClassRepository.save(collegeEntity);
        }
        return collegeEntity;
    }

    @Override
    public CollegeMajorGradeClass insertCompanyIfNotExist(String college, String major, String grade, String company) {
        CollegeMajorGradeClass collegeEntity = findCompanyForUpdate(college, major, grade, company)
                .orElse(null);
        if(collegeEntity == null) {
            collegeEntity = new CollegeMajorGradeClass();
            collegeEntity.setCollege(college);
            collegeEntity.setMajor(major);
            collegeEntity.setGrade(grade);
            collegeEntity.setCompany(company);
            collegeMajorGradeClassRepository.save(collegeEntity);
        }
        return collegeEntity;
    }


    @Override
    public void deleteCollege(String college) {
        collegeMajorGradeClassRepository.deleteAllByCollege(college);
    }

    @Override
    public void deleteMajor(String college, String major) {
        collegeMajorGradeClassRepository.deleteAllByCollegeAndMajor(college, major);
    }

    @Override
    public void deleteGrade(String college, String major, String grade) {
        collegeMajorGradeClassRepository.deleteAllByCollegeAndMajorAndGrade(college, major, grade);
    }

    @Override
    public void deleteCompany(String college, String major, String grade, String company) {
        collegeMajorGradeClassRepository.deleteAllByCollegeAndMajorAndGradeAndCompany(college, major, grade, company);
    }

    @Override
    public void updateCollegeName(String college, String newName) {
        collegeMajorGradeClassRepository.updateCollege(college, newName);
    }

    @Override
    public void updateMajorName(String college, String major, String newName) {
        collegeMajorGradeClassRepository.updateMajor(college, major, newName);
    }

    @Override
    public void updateGradeName(String college, String major, String grade, String newName) {
        collegeMajorGradeClassRepository.updateGrade(college, major, grade, newName);

    }

    @Override
    public void updateCompanyName(String college, String major, String grade, String company, String newName) {
        collegeMajorGradeClassRepository.updateCompany(college, major, grade, company, newName);
    }
}
