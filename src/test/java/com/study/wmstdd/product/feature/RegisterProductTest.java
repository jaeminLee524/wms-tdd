package com.study.wmstdd.product.feature;

import com.study.wmstdd.product.common.ApiTest;
import com.study.wmstdd.product.domain.ProductRepository;
import com.study.wmstdd.product.feature.api.RegisterProductApi;
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
        new RegisterProductApi().request();

        // then
        Assertions.assertThat(productRepository.findAll()).hasSize(1);
    }

}