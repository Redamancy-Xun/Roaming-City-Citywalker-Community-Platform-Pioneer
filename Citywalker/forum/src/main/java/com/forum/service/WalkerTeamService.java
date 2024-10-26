package com.forum.service;

import com.forum.common.Page;
import com.forum.controller.request.CreateWalkerTeamRequest;
import com.forum.controller.response.ShowWalkerTeamResponse;
import com.forum.entity.WalkerTeam;

import java.util.List;

public interface WalkerTeamService {

    ShowWalkerTeamResponse createWalkerTeam(CreateWalkerTeamRequest createWalkerTeamRequest);

    Page<ShowWalkerTeamResponse> showWalkerTeam(Integer page, Integer PageSize);

    ShowWalkerTeamResponse getWalkerTeamById(Long teamId);

    void deleteWalkerTeam(Long teamId);

    Page<ShowWalkerTeamResponse> showUserWalkerTeam(Integer page, Integer PageSize);
}
