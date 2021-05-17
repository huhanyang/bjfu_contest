package com.bjfu.contest.service;

import com.bjfu.contest.pojo.dto.ContestDTO;
import com.bjfu.contest.pojo.dto.UserDTO;
import com.bjfu.contest.pojo.request.teacher.TeacherCreateRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherDeleteRequest;
import com.bjfu.contest.pojo.request.teacher.TeacherListAllTeachContestsRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContestTeacherService {
    /**
     * 添加指导老师
     * @param request 请求
     * @param account 操作人
     * @return 指导教师
     */
    List<UserDTO> create(TeacherCreateRequest request, String account);
    /**
     * 删除指导老师
     * @param request 请求
     * @param account 操作人
     */
    void delete(TeacherDeleteRequest request, String account);
    /**
     * 获取指导教师列表
     * @param contestId 竞赛id
     * @return 指导教师
     */
    List<UserDTO> listAllByContest(Long contestId);
    /**
     * 获取指导的竞赛列表
     * @param request 请求
     * @param account 账号
     * @return 指导教师
     */
    Page<ContestDTO> listAllByAccount(TeacherListAllTeachContestsRequest request, String account);
}
