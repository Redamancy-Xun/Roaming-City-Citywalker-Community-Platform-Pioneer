package com.forum.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumExceptionType {
    SYSTEM_INTERNAL_ANOMALY(1000, "网络不给力，请稍后重试"),
    PASSWORD_INCORRECT(1001, "密码错误"),
    USER_NOT_EXIST(1002,"用户不存在"),
    USER_ALREADY_EXIST_BUT_CAN_UPGRADE(1003,"用户名已存在"),
    ADMIN_ALREADY_EXISTS(1004,"该管理员已存在"),
    LOGIN_INVALID(1,"登录状态失效，请重新登录"),
    PARAMETER_FORMAT_INCORRECT(-2, "参数格式不正确"),
    LENGTH_INCORRECT(1005, "参数长度不正确"),
    EMAIL_INVALID(1006,"邮箱格式不正确" ),
    ARTICLE_ID_NOT_EXIST(1007,"帖子不存在"),
    PERMISSION_NOT_EXIST(1008, "无相关权限"),
    AUTHORIZATION_EXCEPTION(1009, "认证失败"),
    ADMIN_NOT_EXIST(1010,"管理员不存在"),
    STUDENTID_USED(1011,"学号已被使用"),
    ARTICLE_EMPTY(1012,"没有帖子"),
    FILE_FORMAT_ERROE(1013,"文件格式错误"),
    EMPTY_FILE(1014,"文件为空"),
    PORTRAIT_UPDATE_FAILED(1015,"更新失败"),
    NULL_PRINCIPLE(1016,"实体为空"),
    LOGIN_ERROR(1017,"登录失败"),
    NO_SESSION(1018,"未获取到session"),
    DUPLICATE_OF_OLDPASSWORD(1019,"新密码与旧密码重复"),

    ROUTE_NOT_EXIST(1020, "路线不存在"),
    ADD_SHOPPINGCART_FAIL(1021, "加入购物车失败"),
    DELETE_SHOPPINGCART_FAIL(1022, "购物车删除失败"),
    TEAM_NOT_EXIST(1023, "队伍不存在"),
    DELETE_WALKERTEAM_FAIL(1024, "组队删除失败"),

    /**
     * 武学长
     */
    INVALID_SESSION(2006,"会话丢失, 登录已失效, 请重新登录"),
    USER_NOT_ADMIN(2002,"用户非管理员"),
    NEED_SESSION_ID(2003,"未传入sessionId, 请传入会话id"),
    LOGIN_HAS_OVERDUE(2004,"登录已过期"),
    SESSION_IS_INVALID(2005,"该session数据库里没有, 请在header里key为session处对应有效的sessionId"),
    SEND_EMAIL_FAILED(2024, "发送邮件失败, 请检查邮箱账号"),
    EMAIL_HAS_BEEN_SIGNED_UP(2025, "该邮箱已被使用, 请更换邮箱"),
    VERIFICATION_CODE_WRONG(2026, "邮箱验证码错误, 请输入正确的邮箱验证码"),
    EMAIL_NOT_SIGNED_UP(2027, "该邮箱尚未注册账号, 请输入正确的邮箱或先用此邮箱注册账号"),
    PASSWORD_NOT_QUANTIFIED(2028, "密码强度不够, 请输入符合要求的密码"),
    CAN_NOT_DELETE(2030, "超级管理员无法注销, 你这么牛逼, 注销自己干嘛"),
    DO_NOT_SEND_VERIFICATION_CODE_AGAIN(2031, "您上一次验证码尚未失效, 请勿重复发送验证码"),
    CAN_NOT_COMMENT(2033, "该用户没有评论的权限, 请联系超管修改权限"),
    CAN_NOT_MODIFY(2034, "该用户没有修改/删除的权限, 请联系超管修改权限"),
    COMENT_NOT_EXIST(2037, "评论不存在, 请重新检查id"),
    VERIFICATION_CODE_HAS_EXPIRED(2038, "验证码已过期, 请重新发送验证码"),
    READ_FILE_ERROR(2039, "读取文件失败, 请检查文件"),

//    USER_NOT_EXIST(9001,"用户不存在"),
    UPDATE_FAILED(9007,"更新失败, 没有信息被更改"),
    WRONG_CODE(9021,"传入的code有误, 传入的code有误"),
    AUTHEN_NOT_EXIST(9022,"认证不存在"),
    STILL_NOT_VERIFIED(9023,"用户未身份认证"),
    APPLY_EXIST(9024,"已经发送过同样的申请啦！"),
    USER_NOT_THIS_TEAM(9025,"用户不是这个团队的"),
    SYSTEM_ERROR(9026,"系统错误, 手机号获取失败"),
    INPUT_NULL(9027,"输入为空！"),
    EMAIL_PATTERN_ERROR(9028,"邮箱地址有误"),
    PERMISSION_DENIED(9029, "权限不足"),
    WECHAT_ERROR(9030, "微信登录失败"),
    USER_BANNED(9032, "用户被封禁"),
    POST_NOT_EXIST(9031, "帖子不存在"),
    COMMENT_NOT_FOUND(9034, "评论不存在"),
    REPLY_NOT_FOUND(9035, "回复不存在"),
    PARAM_ERROR(9036, "参数错误"),
    STAR_FAILED(9037, "收藏失败"),
    NOTICE_NOT_EXIST(9038, "通知不存在"),
    REDIS_ERROR(9039, "redis错误"),
    USER_NOT_VERIFIED(9040, "用户未认证"),
    SESSION_NOT_FOUND(9041, "session不存在, 请清空wxStorage或重新登录！"),
    USERNAME_EXIST(9042, "名字重复"),
    STAR_EXIST(9043, "已收藏"),
    BOARD_NOT_EXIST(9044, "公告不存在"),
    VIEW_COUNT_ERROR(9045, "浏览量错误"),
    UNSATR_FAIL(9046, "已取消收藏")
    ;

    private final int errorCode;

    private final String codeMessage;

}
