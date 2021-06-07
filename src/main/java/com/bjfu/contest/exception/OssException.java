package com.bjfu.contest.exception;

import com.bjfu.contest.enums.ResultEnum;

/**
 * OSS异常
 * @author warthog
 */
public class OssException extends BaseAppException {
    public OssException(ResultEnum resultEnum) {
        super(resultEnum);
    }
}
