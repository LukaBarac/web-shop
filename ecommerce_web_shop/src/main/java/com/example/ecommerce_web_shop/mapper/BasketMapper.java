package com.example.ecommerce_web_shop.mapper;

import com.example.ecommerce_web_shop.dto.BasketContentsDto;
import com.example.ecommerce_web_shop.dto.BasketDto;
import com.example.ecommerce_web_shop.model.Basket;
import com.example.ecommerce_web_shop.model.BasketContents;
import com.example.ecommerce_web_shop.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BasketMapper {

    public BasketDto map(Basket basket){
        BasketDto basketDto = new BasketDto();
        basketDto.setUserId(basket.getUser().getId());
        if (basket.getBasketContents() == null){    // == null  i  .isEmpty() NIJE ISTO!
            basketDto.setBasketContents(null);  // bez ovog if-a on bi izbacio error da je basketContents null, a ovako sta nece se buniti ako mu ja eksplicitno kazem da jeste zapravo null
        } else {
            basketDto.setBasketContents(basket.getBasketContents().stream().map(bc -> map(bc)).collect(Collectors.toList()));
        }
        return basketDto;
    }

    public BasketContentsDto map(BasketContents basketContents){
        return new BasketContentsDto(basketContents.getBasket().getId(),
                basketContents.getProduct().getId(),
                basketContents.getQuantity());
    }

    public Basket map(User user){
        return new Basket(user);
    }
    // ako mapper pretvara dto u model i obrnuto, zasto onda ovo postoji?
}
