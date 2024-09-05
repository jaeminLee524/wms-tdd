package com.study.wmstdd.product.feature.api;

import com.study.wmstdd.product.common.Scenario;
import com.study.wmstdd.product.domain.Category;
import com.study.wmstdd.product.domain.TemperatureZone;
import com.study.wmstdd.product.feature.RegisterProduct.Request;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class RegisterProductApi {

    private String name = "name";
    private String code = "code";
    private String description = "description";
    private String brand = "brand";
    private String maker = "maker";
    private String origin = "origin";
    private Long weightInGrams = 100L;
    private Long widthInMillimeters = 100L;
    private Long heightInMillimeters = 100L;
    private Long lengthInMillimeters = 100L;

    public RegisterProductApi name(String name) {
        this.name = name;
        return this;
    }

    public Scenario request() {
        // given
        Request request = new Request(
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

        return new Scenario();
    }
}