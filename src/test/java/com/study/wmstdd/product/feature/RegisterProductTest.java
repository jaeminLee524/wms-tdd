package com.study.wmstdd.product.feature;

import com.study.wmstdd.product.common.ApiTest;
import com.study.wmstdd.product.domain.Category;
import com.study.wmstdd.product.domain.ProductRepository;
import com.study.wmstdd.product.domain.TemperatureZone;
import com.study.wmstdd.product.feature.RegisterProduct.Request;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class RegisterProductTest extends ApiTest {
    @Autowired
    private ProductRepository productRepository;

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
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/products")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());

        // then
        Assertions.assertThat(productRepository.findAll()).hasSize(1);
    }

}