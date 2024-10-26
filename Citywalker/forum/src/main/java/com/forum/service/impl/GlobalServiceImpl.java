package com.forum.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.forum.common.CommonConstants;
import com.forum.common.EnumExceptionType;
import com.forum.exception.MyException;
import com.forum.controller.request.ContactRequest;
import com.forum.controller.response.SentenceResponse;
import com.forum.entity.User;
import com.forum.service.GlobalService;
import com.forum.service.UserService;
import com.forum.util.HttpUtil;
import com.forum.util.ImageUtil;
import com.forum.util.MessageUtil;
import com.forum.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.forum.common.CommonConstants.USER_FILE_PATH;

@Service
@Slf4j
public class GlobalServiceImpl implements GlobalService {

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private JavaMailSender jms;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 上传图片
     * @param file 图片文件
     * @return 图片路径
     * @throws MyException 通用异常
     */
    @Override
    public String uploadImage(MultipartFile file) throws MyException {
        //获取文件的原始文件名
        String original = file.getOriginalFilename();
        //生成一个随机的唯一标识符，在文件路径中使用，以确保每个上传的文件都有一个唯一的路径
        String flag = IdUtil.fastSimpleUUID();
        //构建上传文件的存储路径
        String rootFilePath = USER_FILE_PATH + flag + "-" + original;
        try {
            //使用 FileUtil 工具类的 writeBytes 方法将文件数据写入磁盘上的指定路径 rootFilePath
            FileUtil.writeBytes(file.getBytes(), rootFilePath);
            //刚刚上传的文件
            File image = new File(rootFilePath);
            if (image.length() >= 1024 * 1024 / 10) {
                while (image.length() >= 1024 * 1024 / 10) {
                    //调用 ImageUtil 工具类的 scale 方法对图片进行缩放。缩放后的图片将覆盖原有文件，并且缩放比例为 2
                    ImageUtil.scale(rootFilePath, rootFilePath,2,false);
                }
            }
        } catch (IOException e) {
            throw new MyException(EnumExceptionType.READ_FILE_ERROR);
        }
        return flag + "-" + original;
    }

    @Override
    public SentenceResponse getOneSentence(String type) throws MyException {
        Map<String, String> map = new HashMap<>();
        if (type == null || type.equals("")) {
            type = "i";
        }
        map.put("c", type);
        String json = HttpUtil.get("https://v1.hitokoto.cn/", map);
        JSONObject object = JSONObject.parseObject(json);
        return SentenceResponse.builder()
                .sentence(object.getString("hitokoto"))
                .author(object.getString("from_who"))
                .source(object.getString("from"))
                .build();
    }

    @Override
    public Boolean contact(ContactRequest contactRequest) throws MyException{
        try {
            User user = userService.getUserById(sessionUtils.getUserId());
            messageUtil.sendMail(sender, sender,"Bug report from:" + user.getUsername(),
                    contactRequest.getContent(), contactRequest.getImages(), jms);
        } catch (Exception e) {
            if (e instanceof MyException) {
                throw (MyException)e;
            }
            e.printStackTrace();
            throw new MyException(EnumExceptionType.SEND_EMAIL_FAILED);
        }

        return true;
    }

}





