package com.dx.test.framework.base.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.dx.test.framework.base.constant.ReturnCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 给前端发送数据时使用的统一格式
 */
@Data // Tips 让 lombok 自动给所有属性生成 get 和 set 方法, 还有全属性的 toString 打印
@AllArgsConstructor // Tips 让 lombok 自动生成包含全部属性的构造函数
public class ResultModel {

    /**
     * 返回码
     */
    private ReturnCodeEnum returnCode;

    /**
     * 提示语
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    /**
     * 无参构造, 默认成功
     */
    public ResultModel() {
        this.returnCode = ReturnCodeEnum.SUCCESS;
    }

    /**
     * 提示语构造, 默认失败
     *
     * @param message 提示语
     */
    public ResultModel(String message) {
        this.returnCode = ReturnCodeEnum.FAIL;
        this.message = message;
    }

    /**
     * 返回码和提示语构造
     *
     * @param returnCode 返回码
     * @param message    提示语
     */
    public ResultModel(ReturnCodeEnum returnCode, String message) {
        this.returnCode = returnCode;
        this.message = message;
    }

    /**
     * 提示语和数据构造, 默认失败
     *
     * @param message 提示语
     * @param data    数据
     */
    public ResultModel(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    /**
     * Tips 在 Java 对象转换为 Json 对象时, 将 returnCode 的 code 属性值输出为 Json 对象的 code属性
     */
    @JSONField(name = "code")
    public String getCode() {
        return returnCode.getCode();
    }

    /**
     * Tips 设置 returnCode 不参与 Object 转 Json 的过程
     */
    @JSONField(serialize = false)
    public ReturnCodeEnum getReturnCode() {
        return this.returnCode;
    }

}
