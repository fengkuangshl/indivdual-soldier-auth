package com.key.win.rsa.web;

import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.web.BaseResult;
import com.key.win.basic.web.CodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EncryptResponse<T> extends BaseResult implements IEncryptor  {
    @ApiModelProperty("返回实体")
    private T data;

    public EncryptResponse(T data, Integer code, String msg) {
        super(code, msg);
        this.data = data;
    }

    public static <T> EncryptResponse<T> succeed() {
        return succeed(IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

    public static <T> EncryptResponse<T> succeed(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> EncryptResponse<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> EncryptResponse<T> succeed(T model) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

    public static <T> EncryptResponse<T> succeedWith(T data, Integer code, String msg) {
        return new EncryptResponse<T>(data, code, msg);
    }

    public static <T> EncryptResponse<T> failed() {
        return failed("KO");
    }

    public static <T> EncryptResponse<T> failed(String msg) {
        return failed(CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> EncryptResponse<T> failed(Integer code, String msg) {
        return failedWith(null, code, msg);
    }

    public static <T> EncryptResponse<T> failed(T model, String msg) {
        return failedWith(model, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> EncryptResponse<T> failedWith(T data, Integer code, String msg) {
        return new EncryptResponse<T>(data, code, msg);
    }

    public static <T> EncryptResponse<T> result(boolean result) {
        return result ? EncryptResponse.succeed(IndividualSoldierAuthConstantUtils.SUCCEED_INFO) : EncryptResponse.failed(IndividualSoldierAuthConstantUtils.FAILED_INFO);
    }

}