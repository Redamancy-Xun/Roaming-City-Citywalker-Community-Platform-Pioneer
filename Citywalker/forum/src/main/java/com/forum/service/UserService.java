package com.forum.service;

import com.forum.common.Page;
import com.forum.controller.request.UpdateUserRequest;
import com.forum.controller.response.LoginInfo;
import com.forum.entity.Route;
import com.forum.entity.User;
import com.forum.exception.MyException;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserService {

    User signUp(String openid) throws MyException;

    User confirmPrivacyPolicy(String userId);

    User updateUser(UpdateUserRequest updateUserRequest);

    LoginInfo login(String code) throws Exception;

    Page<User> getUserList(Integer page, Integer pageSize) throws MyException;

    User updateUserRole(String userId, Integer role);

    User verifyUser(String userId);

    Boolean sendEmail(String email);

    Boolean verifyCode(String email, String code);

    Page<User> searchByUserName(String name, Integer page, Integer pageSize);

    void deleteUser();

    User getUserById(String userId);

    Map<String, Object> getUserInfo() throws MyException;

    Map<String, Object> me() throws MyException;
}
