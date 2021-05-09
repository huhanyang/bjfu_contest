package com.bjfu.contest.pojo.request.contest;

import com.bjfu.contest.enums.ContestStatusEnum;
import com.bjfu.contest.pojo.request.BasePageAndSorterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 列出创建的竞赛请求
 * @author warthog
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContestListCreatedRequest extends BasePageAndSorterRequest {
    /**
     * 姓名过滤
     */
    private List<String> name;
    /**
     * 状态过滤
     */
    private List<ContestStatusEnum> status;
}
