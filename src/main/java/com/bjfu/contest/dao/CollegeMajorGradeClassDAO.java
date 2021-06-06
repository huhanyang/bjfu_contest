package com.bjfu.contest.dao;


import com.bjfu.contest.pojo.entity.CollegeMajorGradeClass;

import java.util.List;
import java.util.Optional;

public interface CollegeMajorGradeClassDAO {

    List<String> findAllCollege();
    List<CollegeMajorGradeClass> findAllClassByCollege(String college);

    Optional<CollegeMajorGradeClass> findCollegeForUpdate(String college);
    Optional<CollegeMajorGradeClass> findMajorForUpdate(String college, String major);
    Optional<CollegeMajorGradeClass> findGradeForUpdate(String college, String major, String grade);
    Optional<CollegeMajorGradeClass> findCompanyForUpdate(String college, String major, String grade, String company);

    CollegeMajorGradeClass insertCollegeIfNotExist(String college);
    CollegeMajorGradeClass insertMajorIfNotExist(String college, String major);
    CollegeMajorGradeClass insertGradeIfNotExist(String college, String major, String grade);
    CollegeMajorGradeClass insertCompanyIfNotExist(String college, String major, String grade, String company);

    void deleteCollege(String college);
    void deleteMajor(String college, String major);
    void deleteGrade(String college, String major, String grade);
    void deleteCompany(String college, String major, String grade, String company);

    void updateCollegeName(String college, String newName);
    void updateMajorName(String college, String major, String newName);
    void updateGradeName(String college, String major, String grade, String newName);
    void updateCompanyName(String college, String major, String grade, String company, String newName);
}
