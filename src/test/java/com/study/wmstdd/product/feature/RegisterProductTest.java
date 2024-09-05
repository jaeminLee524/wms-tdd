package com.study.wmstdd.product.feature;

import com.study.wmstdd.product.common.ApiTest;
import com.study.wmstdd.product.common.Scenario;
import com.study.wmstdd.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegisterProductTest extends ApiTest {
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품을 등록한다.")
    @Test
    void registerProduct() {
        Scenario.registerProduct().request();

        // then
        Assertions.assertThat(productRepository.findAll()).hasSize(1);
    }

}