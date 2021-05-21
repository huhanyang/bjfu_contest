package com.bjfu.contest.service;

import com.bjfu.contest.pojo.dto.ContestGroupDTO;
import com.bjfu.contest.pojo.dto.ContestProcessDTO;
import com.bjfu.contest.pojo.request.process.ProcessCreateRequest;
import com.bjfu.contest.pojo.request.process.ProcessDemoteGroupsRequest;
import com.bjfu.contest.pojo.request.process.ProcessEditRequest;
import com.bjfu.contest.pojo.request.process.ProcessPromoteGroupsRequest;

import java.util.List;

/**
 * 竞赛流程相关操作服务
 * @author warthog
 */
public interface ContestProcessService {

    /**
     * 获取流程信息
     * @param processId 流程id
     * @return 流程
     */
    ContestProcessDTO getInfo(Long processId);

    /**
     * 列出竞赛里的所有流程
     * @param contestId 竞赛id
     * @return 竞赛流程列表
     */
    List<ContestProcessDTO> listAll(Long contestId);

    /**
     * 创建竞赛流程
     * @param request 请求
     * @param account 账号
     * @return 流程
     */
    ContestProcessDTO create(ProcessCreateRequest request, String account);

    /**
     * 修改竞赛流程信息
     * @param request 请求
     * @param account 账号
     */
    void edit(ProcessEditRequest request, String account);

    /**
     * 删除竞赛流程
     * @param contestId 竞赛id
     * @param processId 流程id
     * @param account 账号
     */
    void delete(Long contestId, Long processId, String account);

    /**
     * 列出可以晋升到此流程的的队伍列表
     * @param targetProcessId 流程id
     * @param account 账号
     * @return 队伍列表
     */
    List<ContestGroupDTO> listPromotableGroups(Long targetProcessId, String account);

    /**
     * 提升队伍到新流程
     * @param request 请求
     * @param account 账号
     */
    void promoteGroups(ProcessPromoteGroupsRequest request, String account);

    /**
     * 从流程中删除队伍
     * @param request 请求
     * @param account 账号
     */
    void demoteGroups(ProcessDemoteGroupsRequest request, String account);
}
