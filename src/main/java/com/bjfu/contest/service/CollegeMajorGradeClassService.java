package com.bjfu.contest.service;

import java.util.List;
import java.util.Map;

public interface CollegeMajorGradeClassService {

    List<String> findAllCollege();
    Map<String, Map<String, List<String>>> findAllClassByCollege(String college);

    void createCollege(String college);
    void changeCollegeName(String college, String newName);
    void deleteCollege(String college);

    void createMajor(String college, String major);
    void updateMajor(String college, String major, String newName);
    void deleteMajor(String college, String major);

    void createGrade(String college, String major, String grade);
    void updateGrade(String college, String major, String grade, String newName);
    void deleteGrade(String college, String major, String grade);

    void createCompany(String college, String major, String grade, String company);
    void updateCompany(String college, String major, String grade, String company, String newName);
    void deleteCompany(String college, String major, String grade, String company);
}
