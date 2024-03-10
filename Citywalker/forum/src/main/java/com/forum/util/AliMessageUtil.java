package com.forum.util;

//import com.aliyuncs.CommonRequest;
//import com.aliyuncs.CommonResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;
//import com.google.gson.Gson;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class AliMessageUtil {
//
//    @Resource(name = "json")
//    Gson json;
//
//    //发送注册验证码
//    public String sendSignUpVerifyCode(String tel) throws ClientException {
//        return sendVerifyCode(tel,"SMS_127162594");
//    }
//
//    //发送忘记密码时找回密码的验证码
//    public String sendForgetPasswordVerifyCode(String tel) throws ClientException {
//        return sendVerifyCode(tel,"SMS_127162594");
//    }
//
//    //发送提醒更新报价短信
//    public void sendRemindToUpdateContent(String tel) throws ClientException {
//        sendMessage(tel,"SMS_163847962",null);
//    }
//
//
//
//    private String sendVerifyCode(String tel,String template_code) throws ClientException {
//
//        String numeric = RandomVerifyCodeUtil.getRandomVerifyCode();
//        Map<String,String> map = new HashMap<>();
//        map.put("code",numeric);
//        sendMessage(tel,template_code,map);
//        return numeric;
//    }
//
//    private void sendMessage(String tel,String template_code,Map<String,String> map) throws ClientException {
//
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIyhcFfw8FjAjq", "o7N7ff5ZdtDlrvpAisMtgCoKhgpv9i");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", tel);
//        request.putQueryParameter("SignName", "资源互助");
//        request.putQueryParameter("TemplateCode", template_code);
//        if(map != null) {
//            request.putQueryParameter("TemplateParam", json.toJson(map));
//        }
//        CommonResponse response = client.getCommonResponse(request);
//        System.out.println(response.getData());
//    }
//
//}
