package com.study.wmstdd.product.domain;

public enum Category {
    ELECTRONICS("전자제품");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
