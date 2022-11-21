package com.example.ecommerce_web_shop.service;

import com.example.ecommerce_web_shop.dto.BasketContentsDto;
import com.example.ecommerce_web_shop.dto.BasketDto;


import java.util.List;

public interface BasketService {

    List<BasketDto> getBaskets();

    BasketDto createBasket(int id);

    BasketDto addContentsToBasket(BasketContentsDto basketContentsDto);

    BasketDto findBasket(int basketId);

    BasketDto removeProductFromBasket(int basketId, int productId, BasketContentsDto basketContentsDto);

    BasketDto emptyBasket(int basketId);
}
