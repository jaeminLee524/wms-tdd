package com.study.wmstdd.product.domain;

public class ProductSize {

    private final Long widthInMillimeters;
    private final Long heightInMillimeters;
    private final Long lengthInMillimeters;

    public ProductSize(Long widthInMillimeters,
        Long heightInMillimeters,
        Long lengthInMillimeters) {
        this.widthInMillimeters = widthInMillimeters;
        this.heightInMillimeters = heightInMillimeters;
        this.lengthInMillimeters = lengthInMillimeters;
    }
}
