package com.bjfu.contest.service;

import com.bjfu.contest.pojo.dto.ContestGroupDTO;
import com.bjfu.contest.pojo.request.group.GroupCreateRequest;
import com.bjfu.contest.pojo.request.group.GroupEditRequest;

import java.util.List;

/**
 * 竞赛队伍相关操作
 * @author warthog
 */
public interface ContestGroupService {

    /**
     * 获取队伍信息
     * @param groupId 队伍id
     * @return 队伍信息
     */
    ContestGroupDTO getInfo(Long groupId);
    /**
     * 获取竞赛的所有队伍列表
     * @param contestId 竞赛id
     * @return 队伍列表
     */
    List<ContestGroupDTO> listAllByContest(Long contestId);
    /**
     * 获取竞赛流程下的所有队伍列表
     * @param processId 竞赛流程id
     * @return 队伍列表
     */
    List<ContestGroupDTO> listAllByProcess(Long processId);
    /**
     * 获取参加的所有队伍列表
     * @param account 用户账号
     * @return 队伍列表
     */
    List<ContestGroupDTO> listAllByMember(String account);
    /**
     * 创建队伍
     * @param request 请求
     * @param account 操作人账号
     * @return 创建的队伍
     */
    ContestGroupDTO create(GroupCreateRequest request, String account);
    /**
     * 编辑队伍信息
     * @param request 请求
     * @param account 操作人账号
     * @return 编辑后的队伍
     */
    void edit(GroupEditRequest request, String account);
    /**
     * 删除队伍
     * @param groupId 队伍id
     * @param account 操作人账号
     */
    void delete(Long groupId, String account);

    /**
     * 获取我的队伍
     * @param contestId 竞赛id
     * @param account 账号
     * @return 队伍信息
     */
    ContestGroupDTO getMyGroupByContest(Long contestId, String account);
}
