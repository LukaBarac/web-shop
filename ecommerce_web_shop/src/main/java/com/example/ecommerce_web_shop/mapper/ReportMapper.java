package com.example.ecommerce_web_shop.mapper;

import com.example.ecommerce_web_shop.dto.CityDto;
import com.example.ecommerce_web_shop.dto.ReportDto;
import com.example.ecommerce_web_shop.model.ProductReport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportMapper {
    public ReportDto map(List<CityDto> cityDtos, ProductReport productReport){
        return new ReportDto(productReport.getProductName(),
                productReport.getProductPrice(),
                cityDtos,
                calculateTotalIncome(cityDtos));
    }

    private double calculateTotalIncome(List<CityDto> cityDtos){
        return cityDtos.stream().map(CityDto::income).toList().stream().reduce(0.0, Double::sum);
    }
}
