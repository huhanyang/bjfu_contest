package com.bjfu.contest.pojo.vo;

import com.bjfu.contest.enums.ContestProcessStatusEnum;
import com.bjfu.contest.pojo.dto.ContestProcessDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ContestProcessVO {

    public ContestProcessVO() {}

    public ContestProcessVO(ContestProcessDTO processDTO) {
        BeanUtils.copyProperties(processDTO, this, "contest");
        this.contest = new ContestVO(processDTO.getContest());
    }

    /**
     * 流程id
     */
    private Long id;
    /**
     * 对应竞赛
     */
    private ContestVO contest;
    /**
     * 流程名
     */
    private String name;
    /**
     * 流程序号
     */
    private Integer sort;
    /**
     * 流程状态
     */
    private ContestProcessStatusEnum status;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 需要提交的内容(json array)
     */
    private String submitList;
    /**
     * 流程开始的时间
     */
    private Date startTime;
    /**
     * 停止提交的时间
     */
    private Date endSubmitTime;
    /**
     * 流程结束的时间
     */
    private Date finishTime;
}
