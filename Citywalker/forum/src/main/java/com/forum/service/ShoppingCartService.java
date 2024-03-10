package com.forum.service;

import com.forum.common.Page;
import com.forum.controller.response.ShowShoppingCartReponse;

public interface ShoppingCartService {

    Page<ShowShoppingCartReponse> showShoppingCart(Integer page, Integer pageSize);

    ShowShoppingCartReponse addShoppingCart(Long routId, Integer routePeople, String routeTime);

    void deleteShoppingCart(Long routeId);

}
