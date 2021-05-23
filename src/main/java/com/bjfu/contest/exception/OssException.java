package com.bjfu.contest.exception;

import com.bjfu.contest.enums.ResultEnum;

public class OssException extends AppException{
    public OssException(ResultEnum resultEnum) {
        super(resultEnum);
    }
}
