package com.bjfu.contest.exception;

import com.bjfu.contest.enums.ResultEnum;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final ResultEnum resultEnum;

    public BizException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }
}
