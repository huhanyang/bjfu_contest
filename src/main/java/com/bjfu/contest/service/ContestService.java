package com.bjfu.contest.service;

import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.request.contest.ContestCreateRequest;
import com.bjfu.contest.pojo.request.contest.ContestEditRequest;
import com.bjfu.contest.pojo.request.contest.ContestListAllRequest;
import com.bjfu.contest.pojo.request.contest.ContestListCreatedRequest;
import org.springframework.data.domain.Page;

/**
 * 竞赛相关操作服务
 * @author warthog
 */
public interface ContestService {

    /**
     * 创建竞赛
     * @param request 请求
     * @param account 操作人账号
     * @return 创建好的竞赛
     */
    ContestDTO create(ContestCreateRequest request, String account);

    /**
     * 编辑竞赛信息
     * @param request 请求
     * @param account 操作人账号
     */
    void edit(ContestEditRequest request, String account);

    /**
     * 删除竞赛
     * @param contestId 竞赛id
     * @param account 操作人账号
     */
    void delete(Long contestId, String account);

    /**
     * 获奖竞赛详细信息
     * @param contestId 竞赛id
     * @return 竞赛详细信息
     */
    ContestDTO getInfo(Long contestId);

    /**
     * 列出创建的竞赛
     * @param request 请求
     * @param account 账号
     * @return 分页竞赛信息
     */
    Page<ContestDTO> listCreated(ContestListCreatedRequest request, String account);

    /**
     * 列出所有竞赛
     * @param request 请求
     * @param account 账号
     * @return 分页竞赛信息
     */
    Page<ContestDTO> listAll(ContestListAllRequest request, String account);

}
