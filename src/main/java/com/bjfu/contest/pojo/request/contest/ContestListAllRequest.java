package com.bjfu.contest.pojo.request.contest;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 列出所有竞赛请求
 * @author warthog
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContestListAllRequest extends BasePageAndSorterRequest {
    /**
     * 姓名过滤
     */
    private List<String> name;
    /**
     * 状态过滤
     */
    private List<ContestStatusEnum> status;
    /**
     * 创建人姓名过滤
     */
    private List<String> creatorName;
    /**
     * 创建人学院过滤
     */
    private List<String> creatorCollege;
}
