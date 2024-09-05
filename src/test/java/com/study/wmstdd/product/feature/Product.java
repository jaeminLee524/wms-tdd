package com.study.wmstdd.product.feature;

public class Product {

    private final String name;
    private final String code;
    private final String description;
    private final String brand;
    private final String maker;
    private final String origin;
    private final Category electronics;
    private final TemperatureZone temperatureZone;
    private final Long weightInGrams;
    private final ProductSize productSize;
    private Long id;

    public Product(
        String name,
        String code,
        String description,
        String brand,
        String maker,
        String origin,
        Category electronics,
        TemperatureZone temperatureZone,
        Long weightInGrams,
        ProductSize productSize) {

        this.name = name;
        this.code = code;
        this.description = description;
        this.brand = brand;
        this.maker = maker;
        this.origin = origin;
        this.electronics = electronics;
        this.temperatureZone = temperatureZone;
        this.weightInGrams = weightInGrams;
        this.productSize = productSize;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
