package com.bjfu.contest.pojo.request.register;

import com.bjfu.contest.enums.ContestRegisterStatusEnum;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterListAllRequest extends BasePageAndSorterRequest {
    /**
     * 竞赛id
     */
    Long contestId;
    /**
     * 报名状态过滤
     */
    private List<ContestRegisterStatusEnum> status;
    /**
     * 报名人姓名过滤
     */
    private List<String> registerName;
    /**
     * 报名人年级过滤
     */
    private List<String> registerGrade;
    /**
     * 报名人学院过滤
     */
    private List<String> registerCollege;
    /**
     * 报名人专业过滤
     */
    private List<String> registerMajor;
}
