package com.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.forum.common.EnumExceptionType;
import com.forum.common.Page;
import com.forum.controller.request.CreateWalkerTeamRequest;
import com.forum.controller.response.ShowShoppingCartReponse;
import com.forum.controller.response.ShowUserResponse;
import com.forum.controller.response.ShowWalkerTeamResponse;
import com.forum.entity.Route;
import com.forum.entity.ShoppingCart;
import com.forum.entity.User;
import com.forum.entity.WalkerTeam;
import com.forum.exception.MyException;
import com.forum.mapper.RouteMapper;
import com.forum.mapper.UserMapper;
import com.forum.mapper.WalkerTeamMapper;
import com.forum.service.UserService;
import com.forum.service.WalkerTeamService;
import com.forum.util.SessionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional
public class WalkerTeamServiceImpl implements WalkerTeamService {

    @Autowired
    WalkerTeamMapper walkerTeamMapper;

    @Autowired
    UserService userService;

    @Autowired
    RouteMapper routeMapper;

    @Autowired
    SessionUtils sessionUtils;

    /**
     * 发起组队
     * @param createWalkerTeamRequest 帖子参数
     * @return 组队信息
     * @throws MyException 通用异常
     */
    @Transactional(rollbackFor = MyException.class)
    @Override
    public ShowWalkerTeamResponse createWalkerTeam(CreateWalkerTeamRequest createWalkerTeamRequest) throws MyException {
        String userId = sessionUtils.getUserId();
        User user = userService.getUserById(userId);
        ShowUserResponse showUser = new ShowUserResponse(user);
        Route route = routeMapper.selectById(createWalkerTeamRequest.getRouteId());

        WalkerTeam walkerTeam = WalkerTeam.builder()
                .anticipate(createWalkerTeamRequest.getAnticipate())
                .introduction(createWalkerTeamRequest.getIntroduction())
                .title(createWalkerTeamRequest.getTitle())
                .routeId(createWalkerTeamRequest.getRouteId())
                .time(createWalkerTeamRequest.getTime())
                .photo(JSON.toJSONString(createWalkerTeamRequest.getPhoto()))
                .topic(JSON.toJSONString(createWalkerTeamRequest.getTopic()))
                .userId(userId)
                .createAt(new Date())
                .build();

        walkerTeamMapper.insert(walkerTeam);

        return new ShowWalkerTeamResponse(walkerTeam, showUser, route);
    }

    /**
     * 找搭子界面
     * @param page 页码
     * @param pageSize 每页数量
     * @return Page<ShowWalkerTeamResponse>
     * @throws MyException 通用异常
     */
    @Override
    public Page<ShowWalkerTeamResponse> showWalkerTeam(Integer page, Integer pageSize) throws MyException {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize, "create_at desc");
        QueryWrapper<WalkerTeam> walkerTeamQueryWrapper = new QueryWrapper<>();
        Page<WalkerTeam> walkerTeamPage = new Page<>(new PageInfo<>(walkerTeamMapper.selectList(walkerTeamQueryWrapper)));

        Page<ShowWalkerTeamResponse> responsePage = new Page<>();
        responsePage.setPageSize(walkerTeamPage.getPageSize());
        responsePage.setPageNum(walkerTeamPage.getPageNum());
        responsePage.setTotal(walkerTeamPage.getTotal());
        responsePage.setPages(walkerTeamPage.getPages());

        List<ShowWalkerTeamResponse> responseList = new ArrayList<>();
        for (WalkerTeam walkerTeam : walkerTeamPage.getItems()) {
            User user = userService.getUserById(walkerTeam.getUserId());
            ShowUserResponse userResponse = new ShowUserResponse(user);
            Route route = routeMapper.selectById(walkerTeam.getRouteId());
            responseList.add(new ShowWalkerTeamResponse(walkerTeam, userResponse, route));
        }
        responsePage.setItems(responseList);

        return responsePage;
    }

    /**
     * 根据teamId获取路线信息
     * @param teamId 组队id
     * @return ShowWalkerTeamResponse
     * @throws MyException 通用异常
     */
    @Override
    public ShowWalkerTeamResponse getWalkerTeamById(Long teamId) throws MyException{
        WalkerTeam walkerTeam = walkerTeamMapper.selectById(teamId);
        if (walkerTeam == null){
            throw new MyException(EnumExceptionType.TEAM_NOT_EXIST);
        }

        User user = userService.getUserById(walkerTeam.getUserId());
        ShowUserResponse userResponse = new ShowUserResponse(user);
        Route route = routeMapper.selectById(walkerTeam.getRouteId());

        return new ShowWalkerTeamResponse(walkerTeam, userResponse, route);
    }

    /**
     * 删除组队信息
     * @param teamId 路线id
     * @throws MyException 通用异常
     */
    @Override
    public void deleteWalkerTeam(Long teamId) {
        if (walkerTeamMapper.deleteById(teamId) == 0) {
            throw new MyException(EnumExceptionType.DELETE_WALKERTEAM_FAIL);
        }
    }

}
