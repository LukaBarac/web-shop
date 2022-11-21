package com.example.ecommerce_web_shop.mapper;

import com.example.ecommerce_web_shop.dto.CityDto;
import com.example.ecommerce_web_shop.model.CityReport;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public CityDto map(CityReport cityReport, Double price){
        return new CityDto(cityReport.getCityName(),
                cityReport.getAmountSold(),
                getTotalIncome(cityReport.getAmountSold(), price));
    }

    private double getTotalIncome(Integer sold, Double price){
        return sold * price;
    }
}
