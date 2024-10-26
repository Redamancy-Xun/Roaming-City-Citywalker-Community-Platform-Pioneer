package com.forum.service;

import com.forum.common.Page;
import com.forum.controller.response.ShowShoppingCartReponse;

import java.util.List;

public interface ShoppingCartService {

    // 展示购物车
    Page<ShowShoppingCartReponse> showShoppingCart(Integer page, Integer pageSize);

    // 加入购物车
    ShowShoppingCartReponse addShoppingCart(Long routId, Integer routePeople, String routeTime);

    // 删除购物车
    void deleteShoppingCart(String orderId);

    // 购买购物车
    void buyShoppingCart(List<String> orderIdList);

}
