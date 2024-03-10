package com.forum.controller;

import com.forum.annotation.Admin;
import com.forum.annotation.Auth;
import com.forum.common.Page;
import com.forum.common.Result;
import com.forum.controller.request.UpdateUserRequest;
import com.forum.controller.response.LoginInfo;
import com.forum.controller.response.ShowRouteResponse;
import com.forum.controller.response.ShowUserResponse;
import com.forum.entity.Route;
import com.forum.entity.User;
import com.forum.exception.MyException;
import com.forum.service.UserService;
import com.forum.util.RedisUtils;
import com.forum.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Validated
@Api("提供对用户调用操作的链接的类")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @PostMapping(value = "/login", produces = "application/json")
    @ApiOperation(value = "登录", response = LoginInfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true, paramType = "query", dataType = "String") })
    public Result login(@NotNull @RequestParam("code") String code) {
        try {
            return Result.success(userService.login(code.trim()));
        } catch (Exception e) {
            if (e instanceof MyException) {
                return Result.result(((MyException) e).getEnumExceptionType());
            }
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping(value = "/check", produces = "application/json")
    @ApiOperation(value = "检测登录状态")
    public Result checkLoginStatus() {
        try {
            return Result.success(sessionUtils.getSessionData() != null);
        } catch (Exception e) {
            return Result.success(false);
        }
    }

    @Auth
    @PostMapping(value = "/confirmPrivacyPolicy", produces = "application/json")
    @ApiOperation(value = "用户同意隐私协议（Role 从 4 改为 3）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
    })
    public Result updateUserPrivacy(@NotNull @RequestParam("userId") String userId) {
        try {
            return Result.success(new ShowUserResponse(userService.confirmPrivacyPolicy(userId)));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/verifyUser", produces = "application/json")
    @ApiOperation(value = "role 改为 2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
    })
    public Result verifyUser(@NotNull @RequestParam("userId") String userId) {
        try {
            return Result.success(new ShowUserResponse(userService.verifyUser(userId)));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/sendCode", produces = "application/json")
    @ApiOperation(value = "发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "email", required = true, paramType = "query", dataType = "String"),
    })
    public Result sendCode(@NotNull @RequestParam("email") String email) {
        try {
            return Result.success(userService.sendEmail(email));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/verifyCode", produces = "application/json")
    @ApiOperation(value = "验证验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "email", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "code", required = true, paramType = "query", dataType = "String")
    })
    public Result verifyCode(@NotNull @RequestParam("email") String email,
                             @NotNull @RequestParam("code") String code) {
        try {
            return Result.success(userService.verifyCode(email, code));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getUserById", produces = "application/json")
    @ApiOperation(value = "获取对应id的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String") })
    public Result getUserById(@NotNull @RequestParam("userId") String userId) {
        try {
            return Result.success(new ShowUserResponse(userService.getUserById(userId.trim())));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/me", produces = "application/json")
    @ApiOperation(value = "‘我的’界面")
    public Result me() {
        try {
            return Result.success(userService.me());
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/getUserInfo", produces = "application/json")
    @ApiOperation(value = "获取用户个人信息")
    public Result getUserInfo() {
        try {
            return Result.success(userService.getUserInfo());
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @GetMapping(value = "/searchByUserName", produces = "application/json")
    @ApiOperation(value = "根据用户名或者签名搜索用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "name", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result searchByUserName(@RequestParam("name") String name,
                                   @RequestParam("page") Integer page,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            Page<User> usersOriginal= userService.searchByUserName(name.trim(), page, pageSize);
            Page<ShowUserResponse> users = new Page<>();
            users.setPages(usersOriginal.getPages());
            users.setTotal(usersOriginal.getTotal());
            users.setPageNum(usersOriginal.getPageNum());
            users.setPageSize(usersOriginal.getPageSize());

            List<ShowUserResponse> showUserResponses = new ArrayList<>();
            for (User user : usersOriginal.getItems()) {
                showUserResponses.add(new ShowUserResponse(user));
            }
            users.setItems(showUserResponses);

            return Result.success(users);
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/updateUser", produces = "application/json")
    @ApiOperation(value = "更改用户信息")
    public Result updateUser(@NotNull @RequestBody UpdateUserRequest updateUserParams) {
        try {
            return Result.success(new ShowUserResponse(userService.updateUser(updateUserParams)));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/logout", produces = "application/json")
    @ApiOperation(value = "清空缓存/退出登录")
    public Result logout() {
        try {
            String userId = sessionUtils.getUserId();
            sessionUtils.invalidate();
            return Result.success(userId);
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Auth
    @PostMapping(value = "/delete", produces = "application/json")
    @ApiOperation(value = "注销用户")
    public Result delete() {
        try {
            userService.deleteUser();
            return Result.success("注销成功");
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Admin
    @GetMapping(value = "/getUserList", produces = "application/json")
    @ApiOperation(value = "获得用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", paramType = "query", dataType = "Integer")
    })
    public Result getUserList(@RequestParam("page") Integer page,
                              @RequestParam("pageSize") Integer pageSize) {
        try {
            Page<User> usersOriginal= userService.getUserList(page, pageSize);
            Page<ShowUserResponse> users = new Page<>();
            users.setPages(usersOriginal.getPages());
            users.setTotal(usersOriginal.getTotal());
            users.setPageNum(usersOriginal.getPageNum());
            users.setPageSize(usersOriginal.getPageSize());

            List<ShowUserResponse> showUserResponses = new ArrayList<>();
            for (User user : usersOriginal.getItems()) {
                showUserResponses.add(new ShowUserResponse(user));
            }
            users.setItems(showUserResponses);

            return Result.success(users);

        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    @Admin
    @PostMapping(value = "/updateUserRole", produces = "application/json")
    @ApiOperation(value = "更改用户role为管理员或者普通用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role", value = "role", required = true, paramType = "query", dataType = "Integer")
    })
    public Result updateUserRole(@NotNull @RequestParam("userId") String userId,
                                 @NotNull @RequestParam("role") Integer role) {
        try {
            return Result.success(userService.updateUserRole(userId, role));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }
}