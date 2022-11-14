package com.example.ecommerce_web_shop.repositories;

import com.example.ecommerce_web_shop.model.BasketContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BasketContentsRepository extends JpaRepository<BasketContents, Integer> {



    Optional<BasketContents> findByBasketIdAndProductId(int basketId, int productId); //mora optional zbog oreslsethrow
    //uzmi da procitas o transactional
    @Transactional
    void deleteByBasketId(int basketId);

    @Transactional
    void deleteByProductIdAndBasketId(int productId, int basketId);
}
