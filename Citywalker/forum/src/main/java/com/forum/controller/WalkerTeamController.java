package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.common.Result;
import com.forum.controller.request.CreateWalkerTeamRequest;
import com.forum.controller.request.UpdateUserRequest;
import com.forum.controller.response.ShowRouteResponse;
import com.forum.controller.response.ShowUserResponse;
import com.forum.exception.MyException;
import com.forum.service.WalkerTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@Validated
@Api("处理找搭子的类")
@RequestMapping("/walkerTeam")
public class WalkerTeamController {

    @Autowired
    private WalkerTeamService walkerTeamService;

    /**
     * 发起组队
     * @param createWalkerTeamRequest
     * @return Result
     */
    @Auth
    @PostMapping(value = "/create", produces = "application/json")
    @ApiOperation(value = "发起组队")
    public Result createWalkerTeam(@NotNull @RequestBody CreateWalkerTeamRequest createWalkerTeamRequest) {
        try {
            return Result.success(walkerTeamService.createWalkerTeam(createWalkerTeamRequest));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    /**
     * 展示组队信息
     * @param page 页码
     * @param pageSize 每页数量
     * @return Result
     */
    @Auth
    @GetMapping(value = "/show", produces = "application/json")
    @ApiOperation(value = "展示组队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result showWalkerTeam(@RequestParam("page") Integer page,
                                 @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(walkerTeamService.showWalkerTeam(page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    /**
     * 获取组队详细信息
     * @param teamId 组队id
     * @return Result
     */
    @Auth
    @GetMapping(value = "/getWalkerTeamById", produces = "application/json")
    @ApiOperation(value = "获取组队详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamId", value = "组队id", required = true, paramType = "query", dataType = "Long") })
    public Result getWalkerTeamById(@NotNull @RequestParam("teamId") Long teamId) {
        try {
            return Result.success(walkerTeamService.getWalkerTeamById(teamId));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    /**
     * 删除组队
     * @param teamId 组队id
     * @return Result
     */
    @Auth
    @PostMapping(value = "/delete", produces = "application/json")
    @ApiOperation(value = "删除组队")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamId", value = "teamId", required = true, paramType = "query", dataType = "Long"),
    })
    public Result deleteWalkerTeam(@RequestParam("teamId") Long teamId) {
        try {
            walkerTeamService.deleteWalkerTeam(teamId);
            return Result.success("删除成功");
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

    /**
     * 展示用户发起的组队信息
     * @param page 页码
     * @param pageSize 每页数量
     * @return Result
     */
    @Auth
    @GetMapping(value = "/showUserWalkerTeam", produces = "application/json")
    @ApiOperation(value = "展示用户发起的组队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
    })
    public Result showUserWalkerTeam(@RequestParam("page") Integer page,
                                     @RequestParam("pageSize") Integer pageSize) {
        try {
            return Result.success(walkerTeamService.showUserWalkerTeam(page, pageSize));
        } catch (MyException e) {
            return Result.result(e.getEnumExceptionType());
        }
    }

}
