package com.bjfu.contest.pojo.request.teacher;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherListAllTeachContestsRequest extends BasePageAndSorterRequest {
    /**
     * 竞赛名过滤
     */
    private List<String> contestName;
    /**
     * 竞赛状态过滤
     */
    private List<ContestStatusEnum> contestStatus;
    /**
     * 创建人姓名过滤
     */
    private List<String> creatorName;
    /**
     * 创建人学院过滤
     */
    private List<String> creatorCollege;
}
