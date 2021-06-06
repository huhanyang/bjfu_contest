package com.bjfu.contest.service.impl;

import com.bjfu.contest.dao.CollegeMajorGradeClassDAO;
import com.bjfu.contest.pojo.entity.CollegeMajorGradeClass;
import com.bjfu.contest.service.CollegeMajorGradeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CollegeMajorGradeClassServiceImpl implements CollegeMajorGradeClassService {

    @Autowired
    private CollegeMajorGradeClassDAO collegeMajorGradeClassDAO;

    @Override
    public List<String> findAllCollege() {
        return collegeMajorGradeClassDAO.findAllCollege();
    }

    @Override
    public Map<String, Map<String, List<String>>> findAllClassByCollege(String college) {
        // key为major value为此major的grades
        Map<String, List<CollegeMajorGradeClass>> majorMap = collegeMajorGradeClassDAO.findAllClassByCollege(college).stream()
                .collect(Collectors.groupingBy(CollegeMajorGradeClass::getMajor));
        Map<String, Map<String, List<String>>> result = new HashMap<>();
        majorMap.forEach((key, value) -> {
            // key为grade value为此grade的companies
            Map<String, List<CollegeMajorGradeClass>> gradeMap = value.stream()
                    .collect(Collectors.groupingBy(CollegeMajorGradeClass::getGrade));
            // company从实体转为string
            Map<String, List<String>> newGradeMap = new HashMap<>();
            gradeMap.forEach((gradeMapKey, gradeMapValue) -> {
                List<String> companyList = gradeMapValue.stream()
                        .map(CollegeMajorGradeClass::getCompany)
                        .collect(Collectors.toList());
                newGradeMap.put(gradeMapKey, companyList);
            });
            result.put(key, newGradeMap);
        });
        return result;
    }

    @Override
    @Transactional
    public void createCollege(String college) {
        collegeMajorGradeClassDAO.insertCollegeIfNotExist(college);
    }

    @Override
    public void changeCollegeName(String college, String newName) {
        collegeMajorGradeClassDAO.updateCollegeName(college, newName);
    }

    @Override
    public void deleteCollege(String college) {
        collegeMajorGradeClassDAO.deleteCollege(college);
    }

    @Override
    @Transactional
    public void createMajor(String college, String major) {
        collegeMajorGradeClassDAO.insertMajorIfNotExist(college, major);
    }

    @Override
    public void updateMajor(String college, String major, String newName) {
        collegeMajorGradeClassDAO.updateMajorName(college, major, newName);
    }

    @Override
    public void deleteMajor(String college, String major) {
        collegeMajorGradeClassDAO.deleteMajor(college, major);
    }

    @Override
    @Transactional
    public void createGrade(String college, String major, String grade) {
        collegeMajorGradeClassDAO.insertGradeIfNotExist(college, major, grade);
    }

    @Override
    public void updateGrade(String college, String major, String grade, String newName) {
        collegeMajorGradeClassDAO.updateGradeName(college, major, grade, newName);
    }

    @Override
    public void deleteGrade(String college, String major, String grade) {
        collegeMajorGradeClassDAO.deleteGrade(college, major, grade);
    }

    @Override
    @Transactional
    public void createCompany(String college, String major, String grade, String company) {
        collegeMajorGradeClassDAO.insertCompanyIfNotExist(college, major, grade, company);
    }

    @Override
    public void updateCompany(String college, String major, String grade, String company, String newName) {
        collegeMajorGradeClassDAO.updateCompanyName(college, major, grade, company, newName);
    }

    @Override
    public void deleteCompany(String college, String major, String grade, String company) {
        collegeMajorGradeClassDAO.deleteCompany(college, major, grade, company);
    }
}
