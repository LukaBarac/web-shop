package com.example.ecommerce_web_shop.controller;

import com.example.ecommerce_web_shop.dto.BasketContentsDto;
import com.example.ecommerce_web_shop.dto.BasketDto;
import com.example.ecommerce_web_shop.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket/")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping("")
    public ResponseEntity<List<BasketDto>> getBaskets(){
        return ResponseEntity.ok(this.basketService.getBaskets());
    }

    @PostMapping("{id}")   // name mu pravi default
    public ResponseEntity<BasketDto> createBasket(@PathVariable(name = "id") int userId){
        return new ResponseEntity<>(basketService.createBasket(userId), HttpStatus.CREATED);
    }

    //dodaj produkte u basket - gotovo

    @PutMapping("")
    public ResponseEntity<BasketDto> addProductToBasket(@RequestBody BasketContentsDto basketContentsDto){
        return ResponseEntity.ok(basketService.addContentsToBasket(basketContentsDto));
    }

    //1. izbaci produkte iz basketa -> moze void sta god

    //2. isprazni basket -> ResponseEntity<BasketDto>


    @GetMapping("ceobasket/{basketId}")
    public ResponseEntity<BasketDto> getBasket(@PathVariable int basketId) {
        return ResponseEntity.ok(basketService.findBasket(basketId));
    }

    @PutMapping("{basketId}/{productId}")
    public ResponseEntity<BasketDto> removeProductFromBasket(@PathVariable int basketId, @PathVariable int productId,@RequestBody BasketContentsDto basketContentsDto){
        return ResponseEntity.ok(basketService.removeProductFromBasket(basketId, productId, basketContentsDto));
    }

    @PutMapping("ceobasket/{basketId}")
    public ResponseEntity<BasketDto> emptyBasket(@PathVariable int basketId){
        return ResponseEntity.ok(basketService.emptyBasket(basketId));
    }
}
