package com.bjfu.contest.pojo.dto;

import com.bjfu.contest.pojo.entity.CollegeMajorGradeClass;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class CollegeMajorGradeClassDTO {

    public CollegeMajorGradeClassDTO() {

    }
    public CollegeMajorGradeClassDTO(CollegeMajorGradeClass collegeMajorGradeClass) {
        if(collegeMajorGradeClass != null) {
            BeanUtils.copyProperties(collegeMajorGradeClass, this);
        }
    }
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date lastModifiedTime;
    /**
     * 学院
     */
    private String college;
    /**
     * 专业
     */
    private String major;
    /**
     * 年级
     */
    private String grade;
    /**
     * 班级
     */
    private String company;

}
