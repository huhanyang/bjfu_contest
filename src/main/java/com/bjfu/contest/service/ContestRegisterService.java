package com.bjfu.contest.service;

import com.bjfu.contest.pojo.dto.ContestRegisterDTO;
import com.bjfu.contest.pojo.request.register.RegisterBanRequest;
import com.bjfu.contest.pojo.request.register.RegisterCreateRequest;
import com.bjfu.contest.pojo.request.register.RegisterListAllRequest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 竞赛报名相关操作服务
 * @author warthog
 */
public interface ContestRegisterService {

    /**
     * 列出所有报名竞赛的用户
     * @param request 请求
     * @return 报名竞赛的人
     */
    Page<ContestRegisterDTO> listAll(RegisterListAllRequest request);

    /**
     * 列出用户报名的所有竞赛
     * @param account 用户账号
     * @return 所有报名过的竞赛
     */
    List<ContestRegisterDTO> listAllRegistered(String account);

    /**
     * 报名竞赛
     * @param request 请求
     * @param account 账号
     */
    void create(RegisterCreateRequest request, String account);

    /**
     * 删除报名
     * @param contestId 竞赛id
     * @param account 参赛人账号
     */
    void delete(Long contestId, String account);

    /**
     * 封禁用户
     * @param request 请求
     * @param account 账号
     */
    void ban(RegisterBanRequest request, String account);

    /**
     * 解封用户
     * @param request 请求
     * @param account 账号
     */
    void unban(RegisterBanRequest request, String account);

    /**
     * 获取用户的注册信息
     * @param contestId 竞赛id
     * @param account 用户账号id
     * @return 注册状态
     */
    ContestRegisterDTO getInfo(Long contestId, String account);
}
