package com.dx.test.business.test.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.dx.test.framework.base.constant.GenderEnum;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息实体类
 */
@Data
public class UserModel {

    /**
     * 名字
     */
    private String name;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 时间
     * Tips 使用 @JSONField 的 format 属性可以定义字符串和日期之间的自动转换规则
     * Tips 如果传入的日期字符串只有年月日, 那么时分秒默认是 12:00:00, 如果年月日也不全, 会报错
     */
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date date;

    /**
     * 联系方式
     */
    private ContactModel contact;

    /**
     * Tips Json 转 Object 时, gender 属性使用此方法
     */
    @JSONField(name = "gender")
    public void setGender(String gender) {
        this.gender = GenderEnum.getEnumByName(gender);
    }

    /**
     * Tips 此方法不参与 Json 转 Object 的过程
     */
    @JSONField(deserialize = false)
    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

}
