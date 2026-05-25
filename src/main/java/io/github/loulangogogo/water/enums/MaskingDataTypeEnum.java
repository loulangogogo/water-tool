package io.github.loulangogogo.water.enums;

/*********************************************************
 ** 脱敏数据类型的枚举值
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public enum MaskingDataTypeEnum {
    /**
     * 手机号码
     *
     * @author :loulan
     */
    PHONE,
    
    /**
     * 邮箱
     *
     * @author :loulan
     */
    EMAIL,
    
    /**
     * 身份证号码
     *
     * @author :loulan
     */
    ID_CARD,
    
    /**
     * 银行卡号
     *
     * @author :loulan
     */
    BANK_CARD,
    
    /**
     * 用户姓名（中国人姓名）
     *
     * @author :loulan
     */
    NAME
    ;
    private MaskingDataTypeEnum() {}
}
