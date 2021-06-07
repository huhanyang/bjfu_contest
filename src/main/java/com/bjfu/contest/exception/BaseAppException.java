package com.bjfu.contest.exception;

import com.bjfu.contest.enums.ResultEnum;
import lombok.Getter;

/**
 * 应用异常基类
 * @author warthog
 */
@Getter
public class BaseAppException extends RuntimeException {

    private final ResultEnum resultEnum;

    public BaseAppException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }
}
