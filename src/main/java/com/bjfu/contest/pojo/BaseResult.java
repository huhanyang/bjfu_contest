package com.bjfu.contest.pojo;

import com.bjfu.contest.enums.ResultEnum;
import lombok.Data;

/**
 * 基本返回值对象
 * @author warthog
 */
@Data
public class BaseResult<T> {

    public BaseResult() {

    }

    public BaseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(Integer code, String msg, T object) {
        this.code = code;
        this.msg = msg;
        this.object = object;
    }

    public BaseResult(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public static BaseResult<Void> success() {
        return new BaseResult<Void>(ResultEnum.SUCCESS);
    }

    public static <T> BaseResult<T> success(T object) {
        return new BaseResult<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), object);
    }

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 对象
     */
    private T object;
}
