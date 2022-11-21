package com.example.ecommerce_web_shop.mapper;



import com.example.ecommerce_web_shop.dto.OrderContentsDto;
import com.example.ecommerce_web_shop.dto.OrderDto;
import com.example.ecommerce_web_shop.model.BasketContents;
import com.example.ecommerce_web_shop.model.Order;
import com.example.ecommerce_web_shop.model.OrderContents;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

   public OrderDto map(Order order){
       OrderDto orderDto = new OrderDto();
       orderDto.setAddress(order.getAddress());
       orderDto.setDateCreated(order.getDateCreated());
       orderDto.setCity(order.getCity());
       orderDto.setTotalPrice(order.getTotalPrice());
       orderDto.setOrderContentsDtos(order.getOrderContents().stream().map(this::map).collect(Collectors.toList()));

       return orderDto;
   }

   public OrderContentsDto map(OrderContents orderContents){
       return new OrderContentsDto(orderContents.getName(),
               orderContents.getQuantity(),
               orderContents.getPrice());
   }
}
