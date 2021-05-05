package com.bjfu.contest.utils;

import com.bjfu.contest.enums.ResultEnum;
import com.bjfu.contest.pojo.BaseResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void writeResultToResponse(ResultEnum resultEnum, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        BaseResult<Void> result = new BaseResult<>(resultEnum);
        ObjectMapper objectMapper = new ObjectMapper();
        writer.println(objectMapper.writeValueAsString(result));
    }
}
