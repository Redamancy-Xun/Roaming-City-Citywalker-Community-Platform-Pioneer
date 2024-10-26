package com.forum.service;

import com.forum.common.Page;
import com.forum.controller.response.ShowRouteResponse;

public interface BuyService {

    Page<ShowRouteResponse> showBuy(Integer page, Integer pageSize);
}
