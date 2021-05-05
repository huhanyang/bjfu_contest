package com.bjfu.contest.exception;

import com.bjfu.contest.enums.ResultEnum;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ResultEnum resultEnum;

    public AppException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }
}
