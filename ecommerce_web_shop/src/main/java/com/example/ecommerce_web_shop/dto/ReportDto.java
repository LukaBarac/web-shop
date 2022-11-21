package com.example.ecommerce_web_shop.dto;

import java.util.List;

public record ReportDto(String name, double price, List<CityDto> cities, double totalIncome) {
}
