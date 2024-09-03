package io.github.loulangogogo.water.test.jdbc;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*********************************************************
 ** 用户表
 ** <br><br>
 ** Date: Created in 2022/6/16 0:14
 ** @author loulan
 ** @version 0.0.0
 *********************************************************/
public class DgUser {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，加密的
     */
    private String password;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 当前账号绑定手机号，要加密处理
     */
    private String phone;

    /**
     * 当前账号绑定邮箱
     */
    private String email;

    /**
     * 微信开放平的unionid
     */
    private String unionid;

    /**
     * 用户状态（10正常，20挂起，30注销）
     */
    private Integer status;

    /**
     * 性别，1男，0女
     */
    private Integer sex;

    /*
    * 存储头像文件的id
    * */
    private Long headerImageFileId;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 身份证号，加密的
     */
    private String idCard;

    /**
     * 苹果用户id
     */
    private String appleId;

    private String nt;

    /*
    * 0表示未领取新人优惠券的用户
    * 10表示已经领取新人优惠券的用户（通知前端进行弹框）
    * 20表示领取完成并通知完成用户
    * */
    private Integer isNewCoupon;
    /**
     * 用户是否被邀请0-未被邀请1-已被邀请
     */
    private Integer isInvited;

    /**
     * 创建时间
     */
    private LocalDateTime crtTime;

    /**
     * 更新时间
     */
    private LocalDateTime uptTime;


    /**
     * 数据状态：null删除，1正常。（使用null是为了再逻辑删除情况下保证唯一索引的正常使用）
     */
    private Integer delFlag;
}