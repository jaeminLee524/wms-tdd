package com.study.wmstdd.product.feature;

import com.study.wmstdd.product.feature.RegisterProduct.Request;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterProductTest {

    private ProductRepository productRepository;
    private RegisterProduct registerProduct;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        registerProduct = new RegisterProduct(productRepository);
    }

    @DisplayName("상품을 등록한다.")
    @Test
    void registerProduct() {
        // given
        final String name = "name";
        String code = "code";
        final String description = "description";
        final String brand = "brand";
        final String maker = "maker";
        final String origin = "origin";
        final Long weightInGrams = 100L;
        final Long widthInMillimeters = 100L;
        final Long heightInMillimeters = 100L;
        final Long lengthInMillimeters = 100L;
        RegisterProduct.Request request = new Request(
            name,
            code,
            description,
            brand,
            maker,
            origin,
            Category.ELECTRONICS,
            TemperatureZone.ROOM_TEMPERATURE,
            weightInGrams,
            widthInMillimeters,
            heightInMillimeters,
            lengthInMillimeters
        );

        // when
        registerProduct.request(request);

        // then
        Assertions.assertThat(productRepository.findAll()).hasSize(1);
    }

}