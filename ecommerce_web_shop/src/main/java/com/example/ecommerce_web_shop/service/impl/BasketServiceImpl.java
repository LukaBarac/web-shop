package com.example.ecommerce_web_shop.service.impl;

import com.example.ecommerce_web_shop.dto.BasketContentsDto;
import com.example.ecommerce_web_shop.dto.BasketDto;
import com.example.ecommerce_web_shop.exception.BadRequestException;
import com.example.ecommerce_web_shop.exception.NotFoundException;
import com.example.ecommerce_web_shop.mapper.BasketMapper;
import com.example.ecommerce_web_shop.model.Basket;
import com.example.ecommerce_web_shop.model.BasketContents;
import com.example.ecommerce_web_shop.model.BasketContentsPK;
import com.example.ecommerce_web_shop.model.Product;
import com.example.ecommerce_web_shop.repositories.BasketContentsRepository;
import com.example.ecommerce_web_shop.repositories.BasketRepository;
import com.example.ecommerce_web_shop.repositories.ProductRepository;
import com.example.ecommerce_web_shop.repositories.UserRepository;
import com.example.ecommerce_web_shop.service.BasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BasketContentsRepository basketContentsRepository;
    @Autowired
    private BasketMapper basketMapper;

    @Override
    public List<BasketDto> getBaskets() {
        return basketRepository.findAll().stream().map(basketMapper::map).collect(Collectors.toList());
    }

    @Override
    public BasketDto createBasket(int userId) {
        var optionalUser = userRepository.findById(userId);  // vraca optional bez orElseThrow();
        if (optionalUser.isPresent()){
            var basket = basketRepository.save(basketMapper.map(optionalUser.get()));
            return basketMapper.map(basket);                        // peske varijanta od orElseThrow();
        } else {
            throw new NotFoundException("user does not exist");
        }
    }

    @Override
    public BasketDto addContentsToBasket(BasketContentsDto basketContentsDto) {

//done
        var productEntity = findProductById(basketContentsDto.getProductId());
//done
        var basketEntity = findBasketById(basketContentsDto.getBasketId());
//done
        var basketContent = createBasketContentEntity(basketEntity, productEntity, basketContentsDto);
//done
        addBasketContentsToBasket(basketEntity, productEntity, basketContent);
//done
        return returnBasketDtoResult(basketEntity);
    }

    @Override
    public BasketDto findBasket(int basketId) {

        var basket = basketRepository.findById(basketId).orElseThrow(() -> new NotFoundException("Not found."));
        return basketMapper.map(basket);
    }

    @Override
    public BasketContentsDto removeProductFromBasket(/*int basketId, int productId, */BasketContentsDto basketContentsDto) {

       /* var product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("no product with that id found"));*/
       /* var basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException("basket not found"));*/
        var basketContent = basketContentsRepository.findByBasketIdAndProductId(basketContentsDto.getBasketId(), basketContentsDto.getProductId())
                .orElseThrow(() -> new NotFoundException("basket does not exist"));

        checkBasketContentQuantityAndUpdate(basketContent, basketContentsDto);

        return basketMapper.map(basketContent);
    }

    public void checkBasketContentQuantityAndUpdate(BasketContents basketContent, BasketContentsDto basketContentsDto){
        var removeQuantity = basketContentsDto.getQuantity();
        var currentQuantity = basketContent.getQuantity();
        if (removeQuantity >= currentQuantity){
            basketContentsRepository.deleteByProductIdAndBasketId(basketContent.getProduct().getId(), basketContent.getBasket().getId());
        } else {
            basketContent.setQuantity(currentQuantity - removeQuantity);
            basketContentsRepository.save(basketContent);
        }
    }

    @Override
//     @Transactional se nalazi u interfejsu
    public BasketDto emptyBasket(int basketId) {

        basketContentsRepository.deleteByBasketId(basketId);    //ovo deleteByBasketId je u sustini deleteById
                                                                    // ali posto je parametar basketId, spring boot provaljuje
                                                                    // sam da nije samo 'id' pa interaktivno menja naziv metode
        var basket = basketRepository.findById(basketId).
                        orElseThrow(() -> new NotFoundException("basket not found"));

        return basketMapper.map(basket);
    }

    //---------------------------- metode za addContentsToBasket -----------------------------------//
    private Product findProductById(int productId) {
            return productRepository.findById(productId)
                            .orElseThrow(() -> new NotFoundException("Product does not exist"));
    }

    private Basket findBasketById(int basketId){
        return basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException("Basket does not exist"));
    }

    private BasketContents createBasketContentEntity(Basket basket, Product product, BasketContentsDto basketContentsDto){
        return new BasketContents(new BasketContentsPK(product.getId(), basket.getId()), basket, product, basketContentsDto.getQuantity());
    }

    private void addBasketContentsToBasket(Basket basket, Product product, BasketContents basketContent){
        if (basketContent.getQuantity() <= product.getStockAmount()){
            BasketContents basketContent2 = basketContentsRepository.save(basketContent);
            basket.getBasketContents().add(basketContent2);
        }else {
            throw new BadRequestException("required quantity is greater than the current amount in stock of this product!");
        }
    }

    private BasketDto returnBasketDtoResult(Basket basket){
        return basketMapper.map(basket);
    }
}


   /* @Override //originalna netaknuta
    public BasketDto removeProductFromBasket(int basketId, int productId, BasketContentsDto basketContentsDto) {

      *//*  var product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("no product with that id found"));*//*

        var basketContent = basketContentsRepository.findByBasketIdAndProductId(basketId, productId)
                .orElseThrow(() -> new NotFoundException("basket does not exist"));

        var removeQuantity = basketContentsDto.getQuantity();
        var currentQuantity = basketContent.getQuantity();
        if (removeQuantity < currentQuantity){
            currentQuantity = currentQuantity - removeQuantity;
            basketContent.setQuantity(currentQuantity);
            basketContentsRepository.save(basketContent);
        } else {
            basketContentsRepository.deleteByProductIdAndBasketId(productId, basketId); // vidi repo
        }

        var basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException("basket not found"));

        return basketMapper.map(basket);
    }
*/