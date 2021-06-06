package com.bjfu.contest.repository;

import com.bjfu.contest.pojo.entity.CollegeMajorGradeClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface CollegeMajorGradeClassRepository extends JpaRepository<CollegeMajorGradeClass, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select cmgc from CollegeMajorGradeClass cmgc where cmgc.college=?1 and cmgc.major is null")
    Optional<CollegeMajorGradeClass> findByCollegeAndMajorIsNullForUpdate(String college);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select cmgc from CollegeMajorGradeClass cmgc where cmgc.college=?1 and cmgc.major=?2 and cmgc.grade is null")
    Optional<CollegeMajorGradeClass> findByCollegeAndMajorAndGradeIsNull(String college, String major);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select cmgc from CollegeMajorGradeClass cmgc where cmgc.college=?1 and cmgc.major=?2 and cmgc.grade=?3 and cmgc.company is null")
    Optional<CollegeMajorGradeClass> findByCollegeAndMajorAndGradeAndCompanyIsNull(String college, String major, String grade);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select cmgc from CollegeMajorGradeClass cmgc where cmgc.college=?1 and cmgc.major=?2 and cmgc.grade=?3 and cmgc.company=?4")
    Optional<CollegeMajorGradeClass> findByCollegeAndMajorAndGradeAndCompany(String college, String major, String grade, String company);

    List<CollegeMajorGradeClass> findAllByMajorIsNull();
    List<CollegeMajorGradeClass> findAllByCollegeAndCompanyIsNotNull(String college);

    void deleteAllByCollege(String college);
    void deleteAllByCollegeAndMajor(String college, String major);
    void deleteAllByCollegeAndMajorAndGrade(String college, String major, String grade);
    void deleteAllByCollegeAndMajorAndGradeAndCompany(String college, String major, String grade, String company);

    @Modifying
    @Query(value = "update CollegeMajorGradeClass cmgc set cmgc.college=?2 where cmgc.college=?1 and cmgc.major is null")
    void updateCollege(String college, String newName);
    @Modifying
    @Query(value = "update CollegeMajorGradeClass cmgc set cmgc.college=?3 where cmgc.college=?1 and cmgc.major =?2 and cmgc.grade is null")
    void updateMajor(String college, String major, String newName);
    @Modifying
    @Query(value = "update CollegeMajorGradeClass cmgc set cmgc.college=?4 where cmgc.college=?1 and cmgc.major =?2 and cmgc.grade=?3 and cmgc.company is null")
    void updateGrade(String college, String major, String grade, String newName);
    @Modifying
    @Query(value = "update CollegeMajorGradeClass cmgc set cmgc.college=?5 where cmgc.college=?1 and cmgc.major =?2 and cmgc.grade=?3 and cmgc.company=?4")
    void updateCompany(String college, String major, String grade, String company, String newName);
}
