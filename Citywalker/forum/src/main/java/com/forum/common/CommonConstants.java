package com.forum.common;

/**
 * @author phoenix
 * @version 2022/1/19 19:21
 */
public class CommonConstants {

    public final static String SESSION = "session";
    public final static String USER_FILE_PATH = "/var/www/html/image/";
    public final static String VERIFICATION_TITLE = "漫游城——citywalker 验证码";
    public final static String AUTH_FILE_PATH = "/home/ubuntu/ver/";
    public final static String IMAGE_PATH = "http://118.178.196.58/image/";
    public final static String AUTH_DOWN_PATH = "http://118.178.196.58/image2/";
    public final static String SEPARATOR = ",";
    public final static String CHAT_RECORD_COLLECTION_NAME = "chat_record";
    public final static String WX_SESSION_REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    public final static String SHADOW_TEST = "shadow";

    //https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml
    public final static String VIEW = "VIEW_COUNT";
    public final static String WX_PAY_REQUEST_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    public final static String CNY_CURRENCY = "CNY";
    public final static String SIGN_TYPE_RSA = "RSA";
    public final static String SIGN_TYPE_HMAC_SHA256 = "HMAC-SHA256";
    public final static String LANG_TYPE_ZH_CN = "zh_CN";
}
