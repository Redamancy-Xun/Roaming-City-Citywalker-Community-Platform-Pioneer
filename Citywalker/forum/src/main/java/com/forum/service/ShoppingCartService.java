package com.forum.service;

import com.forum.common.Page;
import com.forum.controller.response.ShowShoppingCartReponse;

import java.util.List;

public interface ShoppingCartService {

    Page<ShowShoppingCartReponse> showShoppingCart(Integer page, Integer pageSize);

    ShowShoppingCartReponse addShoppingCart(Long routId, Integer routePeople, String routeTime);

    void deleteShoppingCart(Long routeId);

    void buyShoppingCart(List<Long> routeId);

}
