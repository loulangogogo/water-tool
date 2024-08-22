package io.github.loulangogogo.water.enums;

/*********************************************************
 ** 脱敏数据类型的枚举值
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public enum MaskingDataTypeEnum {
    // 手机号码
    PHONE,
    // 邮箱
    EMAIL,
    // 身份证号码
    ID_CARD,
    // 银行卡号
    BANK_CARD,
    // 用户姓名（中国人姓名）
    NAME
    ;
    private MaskingDataTypeEnum() {}
}
