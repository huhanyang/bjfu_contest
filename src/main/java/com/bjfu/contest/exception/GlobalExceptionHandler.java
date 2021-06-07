package com.bjfu.contest.exception;

import com.bjfu.contest.pojo.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @author warthog
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public BaseResult<Void> badParamException(BizException exception){
        return new BaseResult<>(exception.getResultEnum());
    }

    @ResponseBody
    @ExceptionHandler(BaseAppException.class)
    public BaseResult<Void> forTreeException(BaseAppException exception) {
        return new BaseResult<>(exception.getResultEnum());
    }

}
